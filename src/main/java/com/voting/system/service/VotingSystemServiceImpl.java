package com.voting.system.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.voting.system.VotingSystemException;
import com.voting.system.core.Restaurant;
import com.voting.system.core.User;
import com.voting.system.core.Vote;
import com.voting.system.repository.RestaurantRepository;
import com.voting.system.repository.VoteRepository;

@Service
public class VotingSystemServiceImpl implements VotingSystemService {

	private RestaurantRepository restaurantRepository;

	private VoteRepository voteRepository;

    @Autowired
    public VotingSystemServiceImpl(RestaurantRepository restaurantRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }

    /* (non-Javadoc)
	 * @see com.voting.system.service.VotingSystemService#getAllRestaurants()
	 */
    @Override
    @Transactional(readOnly = true)
	public Collection<Restaurant> getAllRestaurants() {
    	return this.restaurantRepository.findAll();
    }

    /* (non-Javadoc)
	 * @see com.voting.system.service.VotingSystemService#addRestaurant(com.voting.system.core.Restaurant)
	 */
    @Override
    @Transactional
    @CachePut(cacheNames="restaurants", key="#result.id")
	public Restaurant addRestaurant(Restaurant restaurant) {
    	return restaurantRepository.save(new Restaurant(restaurant.getName(),
				restaurant.getMenu()));
    }

    /* (non-Javadoc)
	 * @see com.voting.system.service.VotingSystemService#findRestaurantById(java.lang.Long)
	 */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "restaurants", key="#restaurantid")
	public Restaurant findRestaurantById(int restaurantid) {
    	return restaurantRepository.findById(restaurantid);
    }

    /* (non-Javadoc)
	 * @see com.voting.system.service.VotingSystemService#changeLunchMenu(com.voting.system.core.Restaurant, java.lang.Long)
	 */
    @Override
    @Transactional
    @CachePut(cacheNames="restaurants", key="#restaurantid")
	public Restaurant changeLunchMenu(Restaurant r, int restaurantid) throws VotingSystemException {
    	Restaurant restaurant = restaurantRepository.findById(restaurantid);
		if (restaurant != null) {
			restaurant.setMenu(r.getMenu());
			return restaurantRepository.save(restaurant);
		} else {
			throw new VotingSystemException("Restaurant not found");
		}
    }

    /* (non-Javadoc)
	 * @see com.voting.system.service.VotingSystemService#vote(java.lang.Long)
	 */
    @Override
    @Transactional
	public HttpStatus vote(int restaurantid, String userId) throws VotingSystemException {
		if (!StringUtils.isEmpty(userId)) {
			Collection<Vote> votes = voteRepository.findByUserIdAndDate(userId, LocalDate.now());
			if (votes != null && !votes.isEmpty()) {
				
				if (User.isChangedHisMind()) { //is user changed his mind?
					Restaurant restaurant = restaurantRepository.findById(restaurantid);
					if (restaurant != null && restaurant.getId().intValue() != restaurantid) {
						Vote vote = votes.iterator().next();
						vote.setRestaurant(restaurant);
						vote.setVoteDate(LocalDate.now());
						vote.setVoteTime(LocalTime.now());
						voteRepository.save(votes);
						return HttpStatus.OK;
					} else if (restaurant != null) {
						return HttpStatus.NOT_MODIFIED;
					} else {
						throw new VotingSystemException("Restaurant not found");
					}
				} else {
					return HttpStatus.NOT_MODIFIED; 
				}
			} else {
				Restaurant restaurant = restaurantRepository.findById(restaurantid);
				if (restaurant != null) {
					voteRepository.save(new Vote(userId, restaurant, LocalDate.now(), LocalTime.now()));
					return HttpStatus.OK;
				} else {
					throw new VotingSystemException("Restaurant not found");
				}
			}
		} else {
			throw new VotingSystemException("User not found");
		}
    }

    /* (non-Javadoc)
	 * @see com.voting.system.service.VotingSystemService#findVotes()
	 */
    @Override
    @Transactional(readOnly = true)
	public Collection<Vote> findVotes() {
    	return this.voteRepository.findAll();
    }
}
