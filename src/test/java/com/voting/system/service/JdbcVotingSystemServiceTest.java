package com.voting.system.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.voting.system.config.VotingsystemApplication;
import com.voting.system.core.Restaurant;

@ContextConfiguration(classes = VotingsystemApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("jdbc")
public class JdbcVotingSystemServiceTest extends AbstractVotingSystemServiceTest {


    @Test
    public void shouldFindAllRestaurants() {
        Collection<Restaurant> rList = this.votingSystemService.getAllRestaurants();
        assertThat(rList.size()).isEqualTo(2);
    }

    @Test
    public void shouldFindRestaurant() {
    	Restaurant rList = this.votingSystemService.findRestaurantById(1);
        assertThat(rList.getName()).startsWith("testRestaurant");
        assertThat(rList.getMenu().size()).isEqualTo(3);
    }
}