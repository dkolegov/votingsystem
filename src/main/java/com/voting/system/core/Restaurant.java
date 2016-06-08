package com.voting.system.core;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Restaurant extends BaseEntity {

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

	public Restaurant(int id, String name, List<Dish> lunchMenu) {
		super();
		this.id = id;
		this.name = name;
		this.lunchMenu = lunchMenu;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", menu=" + lunchMenu + "]";
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

    protected List<Dish> getDishsInternal() {
        if (this.lunchMenu == null) {
            this.lunchMenu = new ArrayList<>();
        }
        return this.lunchMenu;
    }

    public void addDish(Dish pet) {
    	getDishsInternal().add(pet);
    }
}
