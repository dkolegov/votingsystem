package com.voting.system;
import static com.voting.system.CustomSecurityMockMvcRequestPostProcessors.admin;
import static com.voting.system.CustomSecurityMockMvcRequestPostProcessors.visitor1;
import static com.voting.system.CustomSecurityMockMvcRequestPostProcessors.visitor2;
import static com.voting.system.CustomSecurityMockMvcRequestPostProcessors.visitor3;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;

public class RestServicesTest extends AbstractTest {

	@Test
	public void login() throws Exception {
		// 1
		this.mockMvc.perform(formLogin("/login").user("username","visitor1").password("password","password"))
		.andExpect(redirectedUrl("/"))
        .andExpect(authenticated().withUsername("visitor1"));
		// 2
		HttpHeaders headers = new HttpHeaders();
		headers.add("username", "visitor2");
		headers.add("password", "password");
		headers.add("submit", "Login");
		this.mockMvc.perform(post("/login").headers(headers)).andDo(document("login"));
	}
	
	@Test
	public void addRestaurant() throws Exception {
		this.mockMvc.perform(
				post("/admin/restaurant").content(
						this.json(new Restaurant("testRestaurant",
								new ArrayList<Dish>(){{
									add(new Dish("dish1", 2100));
									add(new Dish("dish2", 2300));
									add(new Dish("dish3", 2400));
								}}
						))
				).with(admin())
        .contentType(contentType))
		.andExpect(status().isCreated()).andDo(document("addrestaurant"));
		this.mockMvc.perform(
				post("/admin/restaurant").content(
						this.json(new Restaurant("testRestaurant2",
								new ArrayList<Dish>(){{
									add(new Dish("dish4", 2100));
									add(new Dish("dish5", 2300));
									add(new Dish("dish6", 2400));
								}}
						))
				).with(admin())
        .contentType(contentType))
		.andExpect(status().isCreated());
	}

	@Test
	public void changeRestaurantMenu() throws Exception {
		this.mockMvc.perform(
				put("/admin/restaurant/1").content(
						this.json(new Restaurant(new Long(1), "",
								new ArrayList<Dish>(){{
									add(new Dish("d1", 2100));
									add(new Dish("d2", 2300));
									add(new Dish("d3", 2400));
								}}
						))
				).with(admin())
        .contentType(contentType))
		.andExpect(status().isOk()).andDo(document("changemenu"))
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void voteVisitor2() throws Exception {
		this.mockMvc.perform(
				post("/vote/1").with(visitor2())).andExpect(status().isOk())
		.andDo(document("vote"));
	}

	@Test
	public void voteVisitor3() throws Exception {
		this.mockMvc.perform(
				post("/vote/1").with(visitor3())).andExpect(status().isOk());
	}

	@Test
	public void voteVisitor1() throws Exception {
		this.mockMvc.perform(
				post("/vote/1").with(visitor1())).andExpect(status().isOk());
		this.mockMvc.perform(
				post("/vote/2").with(visitor1())).andExpect(
						LocalTime.now().isAfter(LocalTime.of(11, 0)) ?
								status().isNotModified() :
								status().isOk());
	}

	@Test
	public void getAll() throws Exception {
		this.mockMvc.perform(
				get("/admin/votes").with(admin()))
					.andExpect(status().isOk())
					.andExpect(content().contentType(contentType))
					.andDo(MockMvcResultHandlers.print());
		this.mockMvc.perform(get("/restaurants").with(visitor1()))
		.andExpect(status().isOk()).andExpect(content().contentType(contentType))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getRestaurant() throws Exception {
		this.mockMvc.perform(get("/restaurants").with(visitor1()))
					.andExpect(status().isOk()).andExpect(content().contentType(contentType));
	}
}
