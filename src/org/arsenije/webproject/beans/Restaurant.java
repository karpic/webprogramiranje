package org.arsenije.webproject.beans;

import java.util.List;

public class Restaurant {
	
	public static enum RestaurantCategory {CHINEESE, INDIAN, PIZZA};
	
	private Long id;
	private String name;
	private String address;
	private RestaurantCategory category;
	private List<Long> dishes;
	private List<Long> drinks;
	private boolean deleted;
	
	public Restaurant() {
		
	}
	
	public Restaurant(Long id, String name, String address, RestaurantCategory category, List<Long> dishes, List<Long> drinks, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.category = category;
		this.dishes = dishes;
		this.drinks = drinks;
		this.deleted = deleted;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public RestaurantCategory getCategory() {
		return category;
	}

	public void setCategory(RestaurantCategory category) {
		this.category = category;
	}

	public List<Long> getDishes() {
		return dishes;
	}

	public void setDishes(List<Long> dishes) {
		this.dishes = dishes;
	}

	public List<Long> getDrinks() {
		return drinks;
	}

	public void setDrinks(List<Long> drinks) {
		this.drinks = drinks;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
