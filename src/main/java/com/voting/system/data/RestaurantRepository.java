package com.voting.system.data;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voting.system.core.Restaurant;

public interface RestaurantRepository  extends JpaRepository<Restaurant, Long> {

	Collection<Restaurant> findByName(String name);
	
	Restaurant findById(Long id);
}
