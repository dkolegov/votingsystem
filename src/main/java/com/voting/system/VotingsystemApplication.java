package com.voting.system;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;
import com.voting.system.core.Visitor;
import com.voting.system.data.RestaurantRepository;
import com.voting.system.data.VisitorRepository;

@SpringBootApplication
public class VotingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingsystemApplication.class, args);
	}
}