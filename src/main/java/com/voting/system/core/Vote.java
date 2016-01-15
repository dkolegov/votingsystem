package com.voting.system.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Vote {

	@Id @GeneratedValue
	private Long id;

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
		return "Vote [id=" + id + ", userId=" + userId + ", restaurant=" + restaurant + ", votedate=" + votedate
				+ ", votetime=" + votetime + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public LocalDate getVoteDate() {
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
