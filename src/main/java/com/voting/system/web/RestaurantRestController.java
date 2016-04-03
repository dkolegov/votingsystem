package com.voting.system.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.voting.system.core.Restaurant;
import com.voting.system.core.Vote;
import com.voting.system.data.RestaurantRepository;
import com.voting.system.data.VoteRepository;

/**
 * Main REST Service of Voting System.
 * @author Dmitry
 *
 */
@RestController
public class RestaurantRestController {

	/**
	 * The repository of restaurants.
	 */
	@Autowired
	RestaurantRepository restaurantRepository;

	/**
	 * The repository which contains visitor's votes.
	 */
	@Autowired
	private VoteRepository voteRepository;

	@RequestMapping(value = "/restaurants", method = RequestMethod.GET)
	Collection<Restaurant> restaurants() {

		return this.restaurantRepository.findAll();
	}

	@RequestMapping(value = "/admin/restaurant",  method = RequestMethod.POST)
	ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant) {

		Restaurant result = restaurantRepository.save(new Restaurant(restaurant.getName(),
				restaurant.getMenu()));
		return new ResponseEntity<>( HttpStatus.CREATED);
	}

	@RequestMapping(value = "/admin/restaurant/{restaurantid}",  method = RequestMethod.PUT)
	ResponseEntity<?> changeLunchMenu(@RequestBody Restaurant r, @PathVariable Long restaurantid) {

		Restaurant restaurant = restaurantRepository.findById(restaurantid);
		if (restaurant != null) {
			restaurant.setMenu(r.getMenu());
			restaurantRepository.save(restaurant);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	@RequestMapping(value = "/vote/{restaurantid}",  method = RequestMethod.POST)
	ResponseEntity<?> vote(@PathVariable Long restaurantid) {

		String userId = userId();
		if (!StringUtils.isEmpty(userId)) {
			synchronized (voteRepository) {
				Collection<Vote> votes = voteRepository.findByUserIdAndDate(userId, LocalDate.now());
				if (votes != null && !votes.isEmpty()) {
					LocalTime time = LocalTime.now();
					if (time.isBefore(LocalTime.of(11, 0))) {
						Restaurant restaurant = restaurantRepository.findById(restaurantid);
						if (restaurant != null) {
							// user changed his mind
							Vote vote = votes.iterator().next();
							vote.setRestaurant(restaurant);
							vote.setVoteDate(LocalDate.now());
							vote.setVoteTime(LocalTime.now());
							voteRepository.save(votes);
							return new ResponseEntity<>(HttpStatus.OK);
						} else {
							return new ResponseEntity<>(HttpStatus.NOT_FOUND);
						}
					} else {
						return new ResponseEntity<>(HttpStatus.NOT_MODIFIED); 
					}
				} else {
					Restaurant restaurant = restaurantRepository.findById(restaurantid);
					if (restaurant != null) {
						voteRepository.save(new Vote(userId, restaurant, LocalDate.now(), LocalTime.now()));
						return new ResponseEntity<>(HttpStatus.OK);
					} else {
						return new ResponseEntity<>(HttpStatus.CONFLICT); // TODO
					}
				}
			}
		} else {
			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT); // TODO
		}
	}

	@RequestMapping(value = "/admin/votes", method = RequestMethod.GET)
	Collection<Vote> votes() {

		return this.voteRepository.findAll();
	}

	private String userId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    return auth.getName();
	}
}
