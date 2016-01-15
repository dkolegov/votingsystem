package com.voting.system;

import java.util.ArrayList;

import javax.swing.text.Document;

import org.junit.Assert;
import org.junit.Test;

import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;
import com.voting.system.core.Visitor;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;


public class VotingsystemApplicationTests extends AbstractTest {

	@Test
	public void restaurants() throws Exception {
		this.mockMvc.perform(get("/restaurants")).andExpect(status().isOk()).andDo(document("restaurant"));
	}
	
	@Test
	public void testVisitors() throws Exception {
		this.visitorRepository.save(new ArrayList<Visitor>() {{
			add(new Visitor("Visitor1"));
			add(new Visitor("Visitor2"));
			add(new Visitor("Visitor3"));
			add(new Visitor("Visitor4"));
		}});
		int i=1;
		for (Visitor v : this.visitorRepository.findAll()) {
			Assert.assertEquals("Visitor"+i++, v.getName());
		}
	}

	
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
}
