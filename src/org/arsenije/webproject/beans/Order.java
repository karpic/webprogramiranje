package org.arsenije.webproject.beans;

import java.util.Date;
import java.util.List;

public class Order {
	
	public static enum OrderStatus {ORDERED, DELIEVERING, CANCELLED, DELIEVERED};
	
	private Long id;
	private List<OrderItem> orderItems;
	private Date dateOrdered;
	private String userUsername;
	private String delievererUsername;
	private OrderStatus status;
	private String note;
	boolean deleted;
	
	public Order() {
		
	}

	public Order(Long id, List<OrderItem> orderItems, Date dateOrdered, String userUsername, String delievererUsername,
			OrderStatus status, String note, List<String> comments, boolean deleted) {
		super();
		this.id = id;
		this.orderItems = orderItems;
		this.dateOrdered = dateOrdered;
		this.userUsername = userUsername;
		this.delievererUsername = delievererUsername;
		this.status = status;
		this.note = note;
		this.deleted = deleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Date getDateOrdered() {
		return dateOrdered;
	}

	public void setDateOrdered(Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	public String getUserUsername() {
		return userUsername;
	}

	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}

	public String getDelievererUsername() {
		return delievererUsername;
	}

	public void setDelievererUsername(String delievererUsername) {
		this.delievererUsername = delievererUsername;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
