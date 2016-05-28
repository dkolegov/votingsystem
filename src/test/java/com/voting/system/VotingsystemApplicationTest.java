package com.voting.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("test")
public class VotingsystemApplicationTest {

	public static void main(String[] args) {
		SpringApplication.run(VotingsystemApplication.class, args);
	}
}