package com.voting.system.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.data.jdbc.core.OneToManyResultSetExtractor;

import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;

public class RestaurantWithDishesExtractor  extends OneToManyResultSetExtractor<Restaurant, Dish, Long> {

	public RestaurantWithDishesExtractor() {
		super(new RestaurnatRowMapper(), new DishRowMapper());
	}

	@Override
	protected void addChild(Restaurant root, Dish child) {
		root.addDish(child);
		
	}

	@Override
	protected Long mapForeignKey(ResultSet rs) throws SQLException {
		if (rs.getObject("dishes.restaurantId") == null) {
		    return null;
		} else {
		    return rs.getLong("dishes.restaurantId");
		}
	}

	@Override
	protected Long mapPrimaryKey(ResultSet rs) throws SQLException {
		return rs.getLong("restaurants.id");
	}
}