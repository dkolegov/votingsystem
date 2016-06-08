package com.voting.system.service;

import java.util.Collection;

import org.springframework.http.HttpStatus;

import com.voting.system.VotingSystemException;
import com.voting.system.core.Restaurant;
import com.voting.system.core.Vote;

public interface VotingSystemService {

	Collection<Restaurant> getAllRestaurants();

	Restaurant addRestaurant(Restaurant restaurant);

	Restaurant findRestaurantById(int restaurantid);

	void changeLunchMenu(Restaurant r, int restaurantid) throws VotingSystemException;

	HttpStatus vote(int restaurantid, String userId) throws VotingSystemException;

	Collection<Vote> findVotes();

}