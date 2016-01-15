package com.voting.system;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;

import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;
import com.voting.system.core.Vote;


public class VotingsystemApplicationTests extends AbstractTest {
	
	@Test
	public void testRestaurants() throws Exception {
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
		this.restaurantRepository.save(new Restaurant("Restaurant3",dishs));

		for (Restaurant r : this.restaurantRepository.findAll()) {
			System.out.println(r.toString());
		}

		for (Restaurant r : this.restaurantRepository.findByName("Restaurant1")) {
			dishs =  new ArrayList<Dish>() {{
				add(new Dish("dish10", 1200));
				add(new Dish("dish11", 1500));
				add(new Dish("dish12", 2000));
			}};
			r.setMenu(dishs);
			this.restaurantRepository.save(r);
		}
		for (Restaurant r : this.restaurantRepository.findByName("Restaurant1")) {
			System.out.println(r.toString());
		}
	}

	
	@Test
	public void testVote() throws Exception {
		ArrayList<Dish> dishs =  new ArrayList<Dish>() {{
			add(new Dish("dish1", 1200));
			add(new Dish("dish2", 1500));
			add(new Dish("dish3", 2000));
		}};
		Restaurant restaurant = new Restaurant("Restaurant777",dishs);

		this.voteRepository.save(new Vote("testVisitor", restaurant, LocalDate.now(), LocalTime.now()));
		dishs =  new ArrayList<Dish>() {{
			add(new Dish("dish1", 1200));
			add(new Dish("dish2", 1500));
			add(new Dish("dish3", 2000));
		}};
		restaurant = new Restaurant("Restaurant666",dishs);
		this.voteRepository.save(new Vote("testVisitor", restaurant, LocalDate.now().minusDays(1), LocalTime.now()));
		dishs =  new ArrayList<Dish>() {{
			add(new Dish("dish1", 1200));
			add(new Dish("dish2", 1500));
			add(new Dish("dish3", 2000));
		}};
		restaurant = new Restaurant("Restaurant555",dishs);
		this.voteRepository.save(new Vote("testVisitor", restaurant, LocalDate.now().plusDays(1), LocalTime.now()));

		System.out.println("ALL:");
		for (Vote vote : this.voteRepository.findByUserId("testVisitor")) {
			System.out.println(vote.toString());
		}
		System.out.println("TODAY ONLY:");
		for (Vote vote : this.voteRepository.findByUserIdAndDate("testVisitor", LocalDate.now())) {
			System.out.println(vote.toString());
		}
		for (Restaurant r : this.restaurantRepository.findAll()) {
			System.out.println(r.toString());
		}
	}
}
