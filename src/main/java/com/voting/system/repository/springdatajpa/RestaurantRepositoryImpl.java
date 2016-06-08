package com.voting.system.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voting.system.core.Restaurant;
import com.voting.system.repository.RestaurantRepository;

public interface RestaurantRepositoryImpl  extends RestaurantRepository, JpaRepository<Restaurant, Integer> {

	@Override
	Collection<Restaurant> findByName(String name);
	
	@Override
	Restaurant findById(Integer id);
}
