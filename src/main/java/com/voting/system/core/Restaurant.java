package com.voting.system.core;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Restaurant {

	@Id @GeneratedValue
	private Long id;

	@NotEmpty
	private String name;

	@OneToMany(fetch = FetchType.EAGER, targetEntity=Dish.class, cascade = CascadeType.ALL)
	private List<Dish> lunchMenu;

	public Restaurant() {
	}

	public Restaurant(String name, List<Dish> lunchMenu) {
		super();
		this.name = name;
		this.lunchMenu = lunchMenu;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", menu=" + lunchMenu + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Dish> getMenu() {
		return lunchMenu;
	}
	public void setMenu(List<Dish> lunchMenu) {
		this.lunchMenu = lunchMenu;
	}
}
