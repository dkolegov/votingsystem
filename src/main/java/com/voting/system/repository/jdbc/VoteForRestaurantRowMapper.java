package com.voting.system.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.voting.system.core.Restaurant;
import com.voting.system.core.Vote;

public class VoteForRestaurantRowMapper implements RowMapper<Vote> {

	@Override
	public Vote mapRow(ResultSet rs, int rowNum) throws SQLException {
		Vote vote = new Vote();
		vote.setUserId(rs.getString("userId"));
		vote.setVoteDate(rs.getDate("votedate").toLocalDate());
		vote.setVoteTime(rs.getTime("votetime").toLocalTime());
		Restaurant r = new Restaurant();
		r.setId(rs.getInt("restaurantId"));
		r.setName(rs.getString("restaurants.name"));
		vote.setRestaurant(r);
        return vote;
	}

}
