package org.arsenije.webproject.beans;

public class Vehicle {
	
	public static enum VehicleType {BICYCLE, SCHOOTER, CAR};
	
	private Long id;
	private String brand;
	private String model;
	private VehicleType type;
	private String registrationNumber;
	private String productionYear;
	private boolean active;
	private String note;
	private boolean deleted;
	
	public Vehicle() {
		
	}

	public Vehicle(long id, String brand, String model, VehicleType type, String registrationNumber, String productionYear,
			boolean active, String note, boolean deleted) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.type = type;
		this.registrationNumber = registrationNumber;
		this.productionYear = productionYear;
		this.active = active;
		this.note = note;
		this.deleted = deleted;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getProductionYear() {
		return productionYear;
	}

	public void setProductionYear(String productionYear) {
		this.productionYear = productionYear;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
