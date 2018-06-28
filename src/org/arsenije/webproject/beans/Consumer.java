package org.arsenije.webproject.beans;

import java.util.List;

public class Consumer{
	
	private Long id;
	private List<Long> orders;
	private List<Long> favoriteRestaurants;
	
	public Consumer() {
		
	}

	public Consumer(Long id, List<Long> orders, List<Long> favoriteRestaurants) {
		super();
		this.id = id;
		this.orders = orders;
		this.favoriteRestaurants = favoriteRestaurants;
	}

	public List<Long> getOrders() {
		return orders;
	}

	public void setOrders(List<Long> orders) {
		this.orders = orders;
	}

	public List<Long> getFavoriteRestaurants() {
		return favoriteRestaurants;
	}

	public void setFavoriteRestaurants(List<Long> favoriteRestaurants) {
		this.favoriteRestaurants = favoriteRestaurants;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
