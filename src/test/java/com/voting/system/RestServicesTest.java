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
				post("/admin/addrestaurant").content(
						this.json(new Restaurant("testRestaurant",
								new ArrayList<Dish>(){{
									add(new Dish("dish21", 2100));
									add(new Dish("dish22", 2300));
									add(new Dish("dish23", 2400));
								}}
						))
				).with(admin())
        .contentType(contentType))
		.andExpect(status().isCreated()).andDo(document("addrestaurant"));
	}

	@Test
	public void changeRestaurantMenu() throws Exception {
		this.mockMvc.perform(
				post("/admin/changemenu/").content(
						this.json(new Restaurant(new Long(2), "Restaurant1",
								new ArrayList<Dish>(){{
									add(new Dish("dish21", 2100));
									add(new Dish("dish22", 2300));
									add(new Dish("dish23", 2400));
								}}
						))
				).with(admin())
        .contentType(contentType))
		.andExpect(status().isOk()).andDo(document("addrestaurant"));
	}

	@Test
	public void voteVisitor2() throws Exception {
		this.mockMvc.perform(
				post("/vote/2").with(visitor2())).andExpect(status().isOk())
		.andDo(document("vote"));
	}

	@Test
	public void voteVisitor3() throws Exception {
		this.mockMvc.perform(
				post("/vote/2").with(visitor3())).andExpect(status().isOk());
	}

	@Test
	public void voteVisitor1() throws Exception {
		this.mockMvc.perform(
				post("/vote/2").with(visitor1())).andExpect(
						LocalTime.now().isAfter(LocalTime.of(11, 0)) ?
								status().isNotModified() :
								status().isOk());
	}
	
	@Test
	public void getrestaurant() throws Exception {
		this.mockMvc.perform(get("/restaurants").with(visitor1())).andExpect(status().isOk()).andExpect(content().contentType(contentType)).andDo(MockMvcResultHandlers.print());
	}
}
