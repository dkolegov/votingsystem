package com.voting.system.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.voting.system.core.Restaurant;

public class RestaurnatRowMapper implements RowMapper<Restaurant> {

	@Override
	public Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
		Restaurant r = new Restaurant();
        r.setId(rs.getInt("restaurants.id"));
        r.setName(rs.getString("restaurants.name"));
        return r;
	}

}
