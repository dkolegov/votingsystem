package com.voting.system.repository.jdbc;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.voting.system.core.Vote;
import com.voting.system.repository.VoteRepository;

@Repository
public class JdbcVoteRepository implements VoteRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertVotes;
    
    private static String SELECT_VOTES =
    		"SELECT id, userId, votedate, votetime, restaurantId, restaurants.name "+
			"FROM votes "+
    		"LEFT OUTER JOIN restaurants ON restaurants.id = votes.restaurantId ";

    @Autowired
    public JdbcVoteRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.insertVotes = new SimpleJdbcInsert(dataSource)
                .withTableName("votes")
                .usingGeneratedKeyColumns("id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }
	@Override
	public Collection<Vote> findByUserId(String userId) {
		Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<Vote> voteList = this.namedParameterJdbcTemplate.query(
        		SELECT_VOTES +
        		"WHERE votes.userId=:userId",
                params,
                new VoteForRestaurantRowMapper()
        );
        return voteList;
	}

	@Override
	public Collection<Vote> findByUserIdAndDate(String userId, LocalDate startdate) {
		Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
		Date date = Date.valueOf(startdate);
		params.put("votedate", date);
		List<Vote> voteList = this.namedParameterJdbcTemplate.query(
        		SELECT_VOTES +
        		"WHERE votes.userId=:userId and votedate=:votedate",
                params,
                new VoteForRestaurantRowMapper()
        );
        return voteList;
	}

	@Override
	public Vote save(Vote vote) {
		Collection<Vote> votesInDB = findByUserIdAndDate(vote.getUserId(), vote.getVoteDate());
		// insert a provided vote
        if (votesInDB.isEmpty()) {
        	Map<String, Object> params = new HashMap<>();
        	params.put("userId", vote.getUserId());
            params.put("votedate", Date.valueOf(vote.getVoteDate()));
            params.put("votetime", Time.valueOf(vote.getVoteTime()));
            params.put("restaurantId", vote.getRestaurantId());
            this.insertVotes.execute(params);
        } else {
	        // insert a related restaurant ID
	        Map<String, Object> params = new HashMap<>();
	        params.put("userId", vote.getUserId());
	        params.put("votedate", Date.valueOf(vote.getVoteDate()));
	        params.put("restaurantId", vote.getRestaurantId());
	        this.namedParameterJdbcTemplate.update(
	                "UPDATE votes SET restaurantId=:restaurantId "+
	                "WHERE userId=:userId AND votedate=:votedate",
	                params);
        }
        return vote;

	}

	@Override
	public Iterable<Vote> save(Iterable<Vote> votes) {
		for (Vote vote : votes) {
			save(vote);
		}
		return votes;
	}

	@Override
	public List<Vote> findAll() {
        List<Vote> voteList = this.namedParameterJdbcTemplate.query(
        		SELECT_VOTES,
                new VoteForRestaurantRowMapper()
        );
        return voteList;
	}

}
