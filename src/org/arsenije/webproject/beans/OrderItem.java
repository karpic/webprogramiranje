package org.arsenije.webproject.beans;

public class OrderItem {
	private Long itemId;
	private String itemName;
	private int quantity;
	
	public OrderItem() {
		
	}

	public OrderItem(Long itemId, String itemName, int quantity) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
