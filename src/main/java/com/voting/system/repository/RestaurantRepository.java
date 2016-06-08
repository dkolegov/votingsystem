package com.voting.system.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.voting.system.core.Restaurant;

public interface RestaurantRepository {

	Collection<Restaurant> findByName(String name) throws DataAccessException;
	
	Restaurant findById(Integer id) throws DataAccessException;

	Restaurant save(Restaurant r) throws DataAccessException;

	List<Restaurant> findAll() throws DataAccessException;
}
