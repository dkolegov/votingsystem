package com.voting.system.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.voting.system.VotingsystemApplication;
import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;
import com.voting.system.core.Vote;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VotingsystemApplication.class)
@ActiveProfiles("spring-data-jpa")
public class SpringDataJpaRepositoryTest {

	@Autowired
	protected RestaurantRepository restaurantRepository;

	@Autowired
	protected VoteRepository voteRepository;
	
	@Test
	public void testRestaurants() throws Exception {
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
		this.restaurantRepository.save(new Restaurant("Restaurant3",dishs));

		for (Restaurant r : this.restaurantRepository.findAll()) {
			System.out.println(r.toString());
		}

		for (Restaurant r : this.restaurantRepository.findByName("Restaurant1")) {
			dishs =  new ArrayList<Dish>() {{
				add(new Dish("dish10", new BigDecimal(12.00)));
				add(new Dish("dish11", new BigDecimal(15.00)));
				add(new Dish("dish12", new BigDecimal(20.00)));
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
			add(new Dish("dish1", new BigDecimal(12.00)));
			add(new Dish("dish2", new BigDecimal(15.00)));
			add(new Dish("dish3", new BigDecimal(20.00)));
		}};
		Restaurant restaurant = new Restaurant("Restaurant777",dishs);

		this.voteRepository.save(new Vote("testVisitor", restaurant, LocalDate.now(), LocalTime.now()));
		dishs =  new ArrayList<Dish>() {{
			add(new Dish("dish1", new BigDecimal(12.00)));
			add(new Dish("dish2", new BigDecimal(15.00)));
			add(new Dish("dish3", new BigDecimal(20.00)));
		}};
		restaurant = new Restaurant("Restaurant666",dishs);
		this.voteRepository.save(new Vote("testVisitor", restaurant, LocalDate.now().minusDays(1), LocalTime.now()));
		dishs =  new ArrayList<Dish>() {{
			add(new Dish("dish1", new BigDecimal(12.00)));
			add(new Dish("dish2", new BigDecimal(15.00)));
			add(new Dish("dish3", new BigDecimal(20.00)));
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
