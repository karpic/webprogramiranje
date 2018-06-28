package org.arsenije.webproject.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.arsenije.webproject.beans.Restaurant;
import org.arsenije.webproject.beans.User;
import org.arsenije.webproject.services.ConsumerService;
import org.arsenije.webproject.services.RestaurantsService;
import org.arsenije.webproject.services.UsersService;

@Path("/restaurants")
public class RestaurantsResource {
	RestaurantsService restaurantsService = new RestaurantsService();
	ConsumerService consumerService = new ConsumerService();
	UsersService userService = new UsersService();
	
	@Context
	HttpServletRequest request;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRestaurants() {
		Collection<Restaurant> restaurants = null;
		try {
			restaurants = this.restaurantsService.getAll();
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

		return Response.ok(restaurants)
						.build();
	}
	
	@Path("/id/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") Long id) {
		Restaurant restaurant = null;
		try {
			restaurant = this.restaurantsService.getRestaurant(id);
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(restaurant).build();
	}
	
	@Path("/category")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRestaurantsByCategoy(@QueryParam("category") String category) {
		Restaurant.RestaurantCategory restaurantCategory = Restaurant.RestaurantCategory.valueOf(category.toUpperCase());
		Collection<Restaurant> restaurants = null;
		try {
			restaurants = this.restaurantsService.getRestaurantsByCategory(restaurantCategory);
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.status(Status.OK)
						.entity(restaurants)
						.build();
	}
	
	@Path("search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchRestaurants( @QueryParam("name") String name,
									   @QueryParam("address") String address,
									   @QueryParam("category") String category) {
		Collection<Restaurant> restaurants = null;
		Restaurant.RestaurantCategory restaurantCategory = null;
		try {
			restaurants = this.restaurantsService.getAll();
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		if(name != null) {
			restaurants = restaurants.stream()
									 .filter((r)->r.getName().equals(name))
									 .collect(Collectors.toCollection(ArrayList::new));
		}
		
		if(address != null) {
			restaurants = restaurants.stream()
								    .filter((r)->r.getAddress().equals(address))
								    .collect(Collectors.toCollection(ArrayList::new));
		}
		
		if(category != null) {
			restaurantCategory = Restaurant.RestaurantCategory.valueOf(category.toUpperCase());
			Restaurant.RestaurantCategory innerCategory = restaurantCategory;
			
			restaurants = restaurants.stream()
									 .filter((r)->r.getCategory().equals(innerCategory))
									 .collect(Collectors.toCollection(ArrayList::new));
			
		}
		
		return Response.ok(restaurants)
						.build();
	}
	
	@Path("/save")
	@POST
	public Response saveRestaurant(@QueryParam("id") Long id) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String username = Authentication.validateToken(authorizationHeader);
		User user = null;
		
		try {
			user = userService.getUser(username);
			this.consumerService.addRestaurantToUser(user.getId(), id);
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/saved")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSavedRestaurants() {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String username = Authentication.validateToken(authorizationHeader);
		User user = null;
		Collection<Restaurant> restaurants = null;
		
		try {
			user = this.userService.getUser(username);
			restaurants = this.restaurantsService.getSavedRestaurants(user.getId());
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(restaurants).build();
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertItem(Restaurant restaurant) {
		try {
			this.restaurantsService.addRestaurant(restaurant);
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError()
							.build();
		}
		
		return Response.status(Status.CREATED)
						.entity(restaurant)
						.build();
	}
	
	@Path("/delete/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteItem(@PathParam("id") long id) {
		try {
			if(this.restaurantsService.removeRestaurant(id)){
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
	public Response updateItem(Restaurant restaurant) {
		try {
			if(this.restaurantsService.updateRestaurant(restaurant)) {
				return Response.status(Status.OK)
								.entity(restaurant)
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
