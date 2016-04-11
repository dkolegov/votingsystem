package com.voting.system.data;

import java.math.BigDecimal;
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
			add(new Dish("dish1", new BigDecimal(12.00)));
			add(new Dish("dish2", new BigDecimal(15.00)));
			add(new Dish("dish3", new BigDecimal(20.00)));
		}};
		this.restaurantRepository.save(new Restaurant("Restaurant1",dishs));
		dishs =  new ArrayList<Dish>() {{
			add(new Dish("dish4", new BigDecimal(12.00)));
			add(new Dish("dish5", new BigDecimal(15.00)));
			add(new Dish("dish6", new BigDecimal(20.00)));
		}};
		this.restaurantRepository.save(new Restaurant("Restaurant2",dishs));
		dishs =  new ArrayList<Dish>() {{
			add(new Dish("dish7", new BigDecimal(12.00)));
			add(new Dish("dish8", new BigDecimal(15.00)));
			add(new Dish("dish9", new BigDecimal(20.00)));
		}};
		Restaurant restaurant = new Restaurant("Restaurant3",dishs);
		this.restaurantRepository.save(restaurant);
		for (Restaurant r : this.restaurantRepository.findAll()) {
			System.out.println(r.toString());
		}
	}
}
