package com.voting.system.core;

import java.math.BigDecimal;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Dish extends BaseEntity {

	@NotEmpty
	private String name;

	private BigDecimal price;

	public Dish(String name, BigDecimal price) {
		super();
		this.name = name;
		this.price = price;
	}

	public Dish() {
	}

	@Override
	public String toString() {
		return "Dish [id=" + id + ", name=" + name + ", price=" + price + "]";
	}

	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
