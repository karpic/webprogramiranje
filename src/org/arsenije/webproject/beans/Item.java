package org.arsenije.webproject.beans;

public class Item  implements Comparable<Item>{
	
	public static enum ItemType {DISH, DRINK};
	
	private Long id;
	private String name;
	private int price;
	private String description;
	private ItemType type;
	private int quantity;
	private int timesOrdered;
	private boolean deleted;
	private long restaurantId;
	
	public Item() {
		
	}

	public Item(Long id, String name, int price, String description, ItemType type, int quantity, int timesOrdered, boolean deleted, long restaurantId) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.type = type;
		this.quantity = quantity;
		this.timesOrdered = timesOrdered;
		this.deleted = deleted;
		this.restaurantId = restaurantId;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTimesOrdered() {
		return timesOrdered;
	}

	public void setTimesOrdered(int timesOrdered) {
		this.timesOrdered = timesOrdered;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
	
	  public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	@Override     
	  public int compareTo(Item item) {          

	    return (this.getTimesOrdered() > item.getTimesOrdered() ? -1 : 

	            (this.getTimesOrdered() == item.getTimesOrdered() ? 0 : 1));     

	  }       
	
}
