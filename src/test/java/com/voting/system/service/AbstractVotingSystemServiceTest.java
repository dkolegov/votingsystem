package com.voting.system.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import com.voting.system.VotingSystemException;
import com.voting.system.core.Dish;
import com.voting.system.core.Restaurant;
import com.voting.system.core.Vote;

public abstract class AbstractVotingSystemServiceTest {

    @Autowired
    protected VotingSystemService votingSystemService;

    @Test
    @Transactional
    public void shouldInsertRestaurant() {
        Collection<Restaurant> rList = this.votingSystemService.getAllRestaurants();
        int found = rList.size();
        
        Restaurant r = new Restaurant();
        r.setName("testServiceRestaraunt");
        List<Dish> lunchMenu = new ArrayList<Dish>(3);
        Dish dish = new Dish("dish1", new BigDecimal(20));
        lunchMenu.add(dish);
        dish = new Dish("dish2", new BigDecimal(21));
        lunchMenu.add(dish);
        dish = new Dish("dish3", new BigDecimal(22));
        lunchMenu.add(dish);
        r.setMenu(lunchMenu);
        
        Restaurant restaurant = this.votingSystemService.addRestaurant(r);
        assertThat(restaurant.getId().longValue()).isNotEqualTo(0);

        rList = this.votingSystemService.getAllRestaurants();
        assertThat(rList.size()).isEqualTo(found + 1);
    }

    @Test
    @Transactional
    public void shouldInsertVote() throws VotingSystemException {
        Collection<Vote> voteList = this.votingSystemService.findVotes();
        int found = voteList.size();
        
        Restaurant restaurant = addTestRestaurant();
        assertThat(restaurant.getId().longValue()).isNotEqualTo(0);

        votingSystemService.vote(restaurant.getId(), "testUser");

        voteList = this.votingSystemService.findVotes();
        assertThat(voteList.size()).isEqualTo(found + 1);
    }

    private Restaurant addTestRestaurant() {
    	Restaurant r = new Restaurant();
        r.setName("testServiceRestaraunt");
        List<Dish> lunchMenu = new ArrayList<Dish>(3);
        Dish dish = new Dish("dish1", new BigDecimal(20));
        lunchMenu.add(dish);
        dish = new Dish("dish2", new BigDecimal(21));
        lunchMenu.add(dish);
        dish = new Dish("dish3", new BigDecimal(22));
        lunchMenu.add(dish);
        r.setMenu(lunchMenu);
        
        return this.votingSystemService.addRestaurant(r);
    }
}