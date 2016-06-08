package com.voting.system.core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class Vote extends BaseEntity {

	private String userId;

	@OneToOne(targetEntity=Restaurant.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Restaurant restaurant;

	private LocalDate votedate;

	private LocalTime votetime;

	public Vote(String userId, Restaurant restaurant, LocalDate date, LocalTime time) {
		super();
		this.userId = userId;
		this.restaurant = restaurant;
		this.votedate = date;
		this.votetime = time;
	}

	public Vote() {
	}

	@Override
	public String toString() {
		return "Vote [userId=" + userId + ", restaurant=" + restaurant + ", votedate=" + votedate
				+ ", votetime=" + votetime + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public Integer getRestaurantId() {
		return restaurant.getId();
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public LocalDate getVoteDate() {
		return votedate;
	}

	public LocalDate getVotedate() {
		return votedate;
	}
	
	public void setVoteDate(LocalDate date) {
		this.votedate = date;
	}

	public LocalTime getVoteTime() {
		return votetime;
	}

	public void setVoteTime(LocalTime votetime) {
		this.votetime = votetime;
	}
}
