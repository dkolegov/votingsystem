package com.voting.system.repository;

import java.util.Collection;
import com.voting.system.core.Restaurant;

public interface RestaurantRepository {

	Collection<Restaurant> findByName(String name);
	
	Restaurant findById(Long id);
}
