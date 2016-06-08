package com.voting.system.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;
import com.voting.system.repository.RestaurantRepository;

@Repository
public class JdbcRestaurantRepository implements RestaurantRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertRestaurant;
    private SimpleJdbcInsert insertDish;
    
    private static String SELECT_RESTAURANTS =
    		"SELECT restaurants.id, restaurants.name, dishes.id, dishes.name, dishes.price, restaurantId "+
    		"FROM restaurants "+
    		"LEFT OUTER JOIN dishes ON restaurants.id = restaurantId ";
    
    private static String DELETE_DISHES = 
    		"DELETE FROM dishes " +
    		"WHERE restaurantId=:restaurantId";

    @Autowired
    public JdbcRestaurantRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.insertRestaurant = new SimpleJdbcInsert(dataSource)
                .withTableName("restaurants")
                .usingGeneratedKeyColumns("id");

        this.insertDish = new SimpleJdbcInsert(dataSource)
                .withTableName("dishes")
                .usingGeneratedKeyColumns("id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }


    /**
     * Loads {@link Owner Owners} from the data store by last name, returning all owners whose last name <i>starts</i> with
     * the given name; also loads the {@link Pet Pets} and {@link Visit Visits} for the corresponding owners, if not
     * already loaded.
     */
    @Override
    public Collection<Restaurant> findByName(String name) throws DataAccessException {
        Map<String, Object> params = new HashMap<>();
        params.put("restaurant_name", name);
        List<Restaurant> rList = this.namedParameterJdbcTemplate.query(
        		SELECT_RESTAURANTS +
        		"WHERE restaurants.name=:restaurant_name",
                params,
                new RestaurantWithDishesExtractor()
        );
        return rList;
    }

	@Override
	public Restaurant findById(Integer id) {
		Map<String, Object> params = new HashMap<>();
        params.put("restaurantId", id);
        List<Restaurant> rList = this.namedParameterJdbcTemplate.query(
        		SELECT_RESTAURANTS+
        		"WHERE restaurants.id=:restaurantId",
                params,
                new RestaurantWithDishesExtractor()
        );
        return rList.get(0);
	}

	@Override
	public Restaurant save(Restaurant r) {
		// insert a provided restaurant
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(r);
        if (r.isNew()) {
            Number newKey = this.insertRestaurant.executeAndReturnKey(parameterSource);
            r.setId(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update(
                    "UPDATE restaurants SET name=:name WHERE id=:id",
                    parameterSource);
        }
        // delete dishes related to the Restaurant from the DB
        SqlParameterSource namedParameters = new MapSqlParameterSource("restaurantId", r.getId());
        this.namedParameterJdbcTemplate.update(
                DELETE_DISHES,
                namedParameters);
        // insert related dishes from the menu of the provided restaurant
        for (Dish dish : r.getMenu()) {
        	Map<String, Object> params = new HashMap<>();
            params.put("name", dish.getName());
            params.put("price", dish.getPrice());
            params.put("restaurantId", r.getId());
        	if (dish.isNew()) {
                Number newKey = this.insertDish.executeAndReturnKey(params);
                dish.setId(newKey.intValue());
            } else {
                this.namedParameterJdbcTemplate.update(
                        "UPDATE dishes SET name=:name, price=:price, restaurantId=:restaurantId WHERE id=:id",
                        params);
            }
        }
        return r;
		
	}

	@Override
	public List<Restaurant> findAll() {
        List<Restaurant> rList = this.namedParameterJdbcTemplate.query(
        		SELECT_RESTAURANTS,
                new RestaurantWithDishesExtractor()
        );
        return rList;
	}
}