package org.arsenije.webproject.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.arsenije.webproject.beans.Vehicle;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VehiclesService {
	private ObjectMapper om;
	private File vehiclesFile;
	
	public VehiclesService() {
		this.om = new ObjectMapper();
		this.vehiclesFile = new File("C:\\Users\\Arsenije\\source\\repos\\web\\NonMavenWebproject\\resources\\vehicles.json");
	}
	
	public Vehicle getVehicleById(Long id) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		vehicles = getAll();
		
		return vehicles.stream()
						.filter((vehicle)-> vehicle.getId() == id)
						.findFirst()
						.orElse(null);
	}
	
	public ArrayList<Vehicle> getAll() throws JsonParseException, JsonMappingException, IOException{
		return om.readValue(vehiclesFile, om.getTypeFactory().constructCollectionLikeType(ArrayList.class, Vehicle.class));
	}
	
	public Vehicle getVehicle(Long id) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		vehicles = getAll();
		
		return vehicles.stream()
				.filter((vehicle)->vehicle.getId() == id)
				.findFirst()
				.orElse(null);
	}
	
	public boolean addVehicle(Vehicle vehicle) throws JsonParseException, JsonMappingException, IOException{
		if( getVehicle(vehicle.getId()) != null) {
			return false;
		}
		
		ArrayList<Vehicle> vehicles = getAll();
		vehicle.setId((long)vehicles.size() + 1);
		vehicle.setActive(true);
		vehicle.setDeleted(false);
		
		vehicles.add(vehicle);
		om.writeValue(vehiclesFile, vehicles);
		return true;
	}
	
	public boolean removeVehicle(long id) throws JsonParseException, JsonMappingException, IOException{
		Vehicle vehicle  = null;
		ArrayList<Vehicle> vehicles = getAll();
		int itemIndex = IntStream.range(0, vehicles.size())
				.filter(i -> vehicles.get(i).getId().equals(id))
				.findFirst()
				.orElse(-1);
		if(itemIndex == -1) {
			return false;
		}
		vehicle = vehicles.get(itemIndex);
		vehicle.setDeleted(true);
		vehicles.set(itemIndex, vehicle);
		om.writeValue(vehiclesFile, vehicles);
		return true;
	}
	
	public boolean updateVehicle(Vehicle vehicle) throws JsonParseException, JsonMappingException, IOException{
		Vehicle vehicleToUpdate  = null;
		ArrayList<Vehicle> vehicles = getAll();
		int itemIndex = IntStream.range(0, vehicles.size())
				.filter(i -> vehicles.get(i).getId().equals(vehicle.getId()))
				.findFirst()
				.orElse(-1);
		if(itemIndex == -1) {
			return false;
		}
		vehicleToUpdate = vehicles.get(itemIndex);
		
		vehicleToUpdate.setActive(vehicle.isActive());
		vehicleToUpdate.setBrand(vehicle.getBrand());
		vehicleToUpdate.setModel(vehicle.getModel());
		vehicleToUpdate.setType(vehicle.getType());
		vehicleToUpdate.setRegistrationNumber(vehicle.getRegistrationNumber());
		vehicleToUpdate.setProductionYear(vehicle.getProductionYear());
		vehicleToUpdate.setNote(vehicle.getNote());
		vehicleToUpdate.setDeleted(vehicle.isDeleted());
		
		
		vehicles.set(itemIndex, vehicleToUpdate);
		om.writeValue(vehiclesFile, vehicles);
		return true;
	}
}
