package com.voting.system;
import java.util.ArrayList;

import javax.swing.text.Document;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;
import com.voting.system.core.Visitor;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;

public class RestServicesTest extends AbstractTest {


	@Test
	public void login() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("username", "visitor1");
		headers.add("password", "password");
		headers.add("submit", "Login");
		this.mockMvc.perform(post("/login").headers(headers)).andDo(document("login"));
	}

	@Test
	public void restaurants() throws Exception {
		this.mockMvc.perform(get("/restaurants")).andExpect(status().isOk()).andDo(document("restaurant"));
	}
	
	@Test
	public void addrestaurant() throws Exception {
		this.mockMvc.perform(
				post("/admin/addrestaurant").content(
						this.json(new Restaurant(new Long(123),"testRestaurant",
								new ArrayList<Dish>(){{
									add(new Dish("dish21", 2100));
									add(new Dish("dish22", 2300));
									add(new Dish("dish23", 2400));
								}}
						))
				)
        .contentType(contentType))
		.andExpect(status().isCreated()).andDo(document("addrestaurant"));
	}
	
	
	@Test
	public void getrestaurant() throws Exception {
		this.mockMvc.perform(get("/restaurants")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
}
