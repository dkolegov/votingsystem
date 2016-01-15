package com.voting.system.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;
import com.voting.system.core.Vote;

public class MockData {

	RestaurantRepository restaurantRepository;

	private VoteRepository voteRepository;

	public MockData(VoteRepository voter, RestaurantRepository rr) {
		this.restaurantRepository = rr;
		this.voteRepository = voter;
	}
	
	public void create() {
		this.restaurantRepository.deleteAllInBatch();
		this.voteRepository.deleteAllInBatch();
		ArrayList<Dish> dishs =  new ArrayList<Dish>() {{
			add(new Dish("dish1", 1200));
			add(new Dish("dish2", 1500));
			add(new Dish("dish3", 2000));
		}};
		this.restaurantRepository.save(new Restaurant("Restaurant1",dishs));
		dishs =  new ArrayList<Dish>() {{
			add(new Dish("dish4", 1200));
			add(new Dish("dish5", 1500));
			add(new Dish("dish6", 2000));
		}};
		this.restaurantRepository.save(new Restaurant("Restaurant2",dishs));
		dishs =  new ArrayList<Dish>() {{
			add(new Dish("dish7", 1200));
			add(new Dish("dish8", 1500));
			add(new Dish("dish9", 2000));
		}};
		Restaurant restaurant = new Restaurant("Restaurant3",dishs);
		this.restaurantRepository.save(restaurant);
		
		this.voteRepository.save(new Vote("visitor1", restaurant, LocalDate.now(), LocalTime.now()));
	}
}
