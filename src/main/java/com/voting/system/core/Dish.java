package com.voting.system.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Dish {

	@Id @GeneratedValue
	private Long dishid;

	@NotEmpty
	private String name;

	// use int just because its not a real app (price in cents)
	private Integer price;

	public Dish(String name, Integer price) {
		super();
		this.name = name;
		this.price = price;
	}

	public Dish() {
	}

	public Long getDishid() {
		return dishid;
	}

	public void setDishid(Long dishid) {
		this.dishid = dishid;
	}

	@Override
	public String toString() {
		return "Dish [id=" + dishid + ", name=" + name + ", price=" + price + "]";
	}

	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
