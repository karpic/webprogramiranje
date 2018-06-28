package org.arsenije.webproject.resources;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import org.arsenije.webproject.beans.Vehicle;
import org.arsenije.webproject.services.VehiclesService;

@Path("/vehicles")
public class VehiclesResource {
	VehiclesService vehiclesService = new VehiclesService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRestaurants() {
		Collection<Vehicle> vehicles = null;
		try {
			vehicles = this.vehiclesService.getAll();
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(vehicles)
						.build();
	}
	
	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneById(@PathParam("id") Long id) {
		Vehicle vehicle = null;
		try {
			vehicle = this.vehiclesService.getVehicleById(id);
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(vehicle).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertItem(Vehicle vehicle) {
		try {
			this.vehiclesService.addVehicle(vehicle);
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError()
							.build();
		}
		
		return Response.status(Status.CREATED)
						.entity(vehicle)
						.build();
	}
	
	@Path("/delete/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteItem(@PathParam("id") long id) {
		try {
			if(this.vehiclesService.removeVehicle(id)){
				return Response.ok(true).build();
			}
			else{
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("/update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateItem(Vehicle vehicle) {
		try {
			if(this.vehiclesService.updateVehicle(vehicle)) {
				return Response.status(Status.OK)
								.entity(vehicle)
								.build();
			}else {
				return Response.serverError().build();
			}
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
