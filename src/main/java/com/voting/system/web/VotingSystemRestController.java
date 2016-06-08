package com.voting.system.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.voting.system.VotingSystemException;
import com.voting.system.core.Restaurant;
import com.voting.system.core.User;
import com.voting.system.core.Vote;
import com.voting.system.service.VotingSystemService;

/**
 * Main REST Service of Voting System.
 * @author Dmitry
 *
 */
@RestController
public class VotingSystemRestController {

	@Autowired
	VotingSystemService votingSystemService;

	@RequestMapping(value = "/restaurants", method = RequestMethod.GET)
	Collection<Restaurant> restaurants() {

		return votingSystemService.getAllRestaurants();
	}

	@RequestMapping(value = "/admin/restaurant",  method = RequestMethod.POST)
	ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant) {

		Restaurant result = votingSystemService.addRestaurant(restaurant);
		return new ResponseEntity<>( HttpStatus.CREATED);
	}

	@RequestMapping(value = "/admin/restaurant/{restaurantid}",  method = RequestMethod.PUT)
	ResponseEntity<?> changeLunchMenu(@RequestBody Restaurant r, @PathVariable int restaurantid) {
		try {
			votingSystemService.changeLunchMenu(r, restaurantid);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (VotingSystemException ex) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	@RequestMapping(value = "/vote/{restaurantid}",  method = RequestMethod.POST)
	ResponseEntity<?> vote(@PathVariable int restaurantid) {

		try {
			HttpStatus httpStatus = votingSystemService.vote(restaurantid, User.getId());
			return new ResponseEntity<>(httpStatus);
		} catch (VotingSystemException ex) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/admin/votes", method = RequestMethod.GET)
	Collection<Vote> votes() {

		return votingSystemService.findVotes();
	}
}
