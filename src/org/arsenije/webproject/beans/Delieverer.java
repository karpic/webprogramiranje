package org.arsenije.webproject.beans;

import java.util.List;

public class Delieverer {
	
	private Long id;
	private Long vehicle;
	private List<Long> ordersToDeliever;
	private boolean active;
	
	public Delieverer() {
		
	}

	public Delieverer(Long id, Long vehicle, List<Long> ordersToDeliever, boolean active) {
		super();
		this.id = id;
		this.vehicle = vehicle;
		this.ordersToDeliever = ordersToDeliever;
		this.active = active;
	}

	public Long getVehicle() {
		return vehicle;
	}

	public void setVehicle(Long vehicle) {
		this.vehicle = vehicle;
	}

	public List<Long> getOrdersToDeliever() {
		return ordersToDeliever;
	}

	public void setOrdersToDeliever(List<Long> ordersToDeliever) {
		this.ordersToDeliever = ordersToDeliever;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
