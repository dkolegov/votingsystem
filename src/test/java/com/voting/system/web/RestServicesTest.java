 package com.voting.system.web;
import static com.voting.system.web.CustomSecurityMockMvcRequestPostProcessors.admin;
import static com.voting.system.web.CustomSecurityMockMvcRequestPostProcessors.visitor1;
import static com.voting.system.web.CustomSecurityMockMvcRequestPostProcessors.visitor2;
import static com.voting.system.web.CustomSecurityMockMvcRequestPostProcessors.visitor3;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.Filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.voting.system.config.VotingsystemApplication;
import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VotingsystemApplication.class)
@ActiveProfiles("jdbc")
@WebAppConfiguration
public class RestServicesTest {

	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    protected MockMvc mockMvc;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
	
	@Rule
	public RestDocumentation restDocumentation = new RestDocumentation("bin/generated-snippets");
	
	@Autowired
	private Filter springSecurityFilterChain;

	protected HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders
	              .webAppContextSetup(webApplicationContext)
	              .apply(documentationConfiguration(this.restDocumentation))
	              .addFilters(springSecurityFilterChain)
	              .build();
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

	@Test
	public void login() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("username", "visitor2");
		headers.add("password", "qwerty");
		headers.add("submit", "Login");
		this.mockMvc.perform(post("/login").headers(headers)).andDo(document("login"));
	}
	
	@Test
	public void addRestaurant() throws Exception {
		this.mockMvc.perform(
				post("/admin/restaurant").content(
						this.json(new Restaurant("testRestaurant",
								new ArrayList<Dish>(){{
									add(new Dish("dish1", new BigDecimal(21.00)));
									add(new Dish("dish2", new BigDecimal(23.00)));
									add(new Dish("dish3", new BigDecimal(24.00)));
								}}
						))
				).with(admin())
        .contentType(contentType))
		.andExpect(status().isCreated()).andDo(document("addrestaurant"));
		this.mockMvc.perform(
				post("/admin/restaurant").content(
						this.json(new Restaurant("testRestaurant2",
								new ArrayList<Dish>(){{
									add(new Dish("dish4", new BigDecimal(21.00)));
									add(new Dish("dish5", new BigDecimal(23.00)));
									add(new Dish("dish6", new BigDecimal(24.00)));
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
						this.json(new Restaurant(1, "",
								new ArrayList<Dish>(){{
									add(new Dish("d1", new BigDecimal(21.00)));
									add(new Dish("d2", new BigDecimal(23.00)));
									add(new Dish("d3", new BigDecimal(24.00)));
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
