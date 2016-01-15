package com.voting.system.core;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Vote {

	@Id @GeneratedValue
	private Long id;

	private String userId;

	@OneToOne(targetEntity=Restaurant.class)
	private Restaurant restaurant;

	private LocalDateTime date;

	public Vote(String userId, Restaurant restaurant, LocalDateTime date) {
		super();
		this.userId = userId;
		this.restaurant = restaurant;
		this.date = date;
	}

	public Vote(Long id, String userId, Restaurant restaurant, LocalDateTime date) {
		super();
		this.id = id;
		this.userId = userId;
		this.restaurant = restaurant;
		this.date = date;
	}

	public Vote() {
	}

	@Override
	public String toString() {
		return "Vote [id=" + id + ", userId=" + userId + ", restaurant=" + restaurant + ", date=" + date + "]";
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
