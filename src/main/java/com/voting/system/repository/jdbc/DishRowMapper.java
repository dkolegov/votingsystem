package com.voting.system.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.voting.system.core.Dish;

public class DishRowMapper implements RowMapper<Dish> {

	@Override
	public Dish mapRow(ResultSet rs, int rowNum) throws SQLException {
		Dish dish = new Dish();
		dish.setId(rs.getInt("dishes.id"));
		dish.setName(rs.getString("dishes.name"));
		dish.setPrice(rs.getBigDecimal("dishes.price"));
        return dish;
	}

}
