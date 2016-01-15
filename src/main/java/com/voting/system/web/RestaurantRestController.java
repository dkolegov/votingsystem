package com.voting.system.web;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;
import com.voting.system.core.Vote;
import com.voting.system.data.MockData;
import com.voting.system.data.RestaurantRepository;
import com.voting.system.data.VisitorRepository;
import com.voting.system.data.VoteRepository;

@RestController
public class RestaurantRestController {

	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	private VisitorRepository visitorRepository;

	@Autowired
	private VoteRepository voteRepository;
	
	private boolean mockDataCreated;

	@RequestMapping(value = "/restaurants", method = RequestMethod.GET)
	Collection<Restaurant> restaurants() {
		if (!mockDataCreated) {
			new MockData(voteRepository, visitorRepository, restaurantRepository).create();
			mockDataCreated = true;
		}
		return this.restaurantRepository.findAll();
	}

	@RequestMapping(value = "/admin/addrestaurant",  method = RequestMethod.POST)
	ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant) {
		Restaurant result = restaurantRepository.save(new Restaurant(restaurant.getName(),
				restaurant.getMenu()));
		return new ResponseEntity<>( HttpStatus.CREATED);
	}

	@RequestMapping(value = "/admin/changemenu/{restaurantid}",  method = RequestMethod.POST)
	ResponseEntity<?> changeLunchMenu(@PathVariable Long restaurantid, @RequestBody List<Dish> lunchMenu) {
		Restaurant restaurant = restaurantRepository.findById(restaurantid);
		if (restaurant != null) {
			restaurant.setMenu(lunchMenu);
			restaurantRepository.save(restaurant);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}


	@RequestMapping(value = "/vote/{restaurantid}",  method = RequestMethod.POST)
	ResponseEntity<?> vote(@PathVariable Long restaurantid) {
		String userId = userId();
		Vote vote = voteRepository.findByUserIdAndToday(userId);
		if (vote != null) {
			LocalTime time = LocalTime.now();
			if (time.isBefore(LocalTime.of(11, 0))) {
				Restaurant restaurant = restaurantRepository.findById(restaurantid);
				if (restaurant != null) {
					// user changed his mind
					vote.setRestaurant(restaurant);
					vote.setDate(LocalDateTime.now());
					voteRepository.save(vote);
					return new ResponseEntity<>(HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_MODIFIED); 
			}
		} else {
			Restaurant restaurant = restaurantRepository.findById(restaurantid);
			if (restaurant != null) {
				voteRepository.save(new Vote(userId, restaurant, LocalDateTime.now()));
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
	}

	private String userId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    return auth.getName();
	}
}
