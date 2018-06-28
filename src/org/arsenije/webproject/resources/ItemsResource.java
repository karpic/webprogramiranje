package org.arsenije.webproject.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.arsenije.webproject.beans.Item;
import org.arsenije.webproject.beans.Restaurant;
import org.arsenije.webproject.services.ItemsService;
import org.arsenije.webproject.services.RestaurantsService;

@Path("/items")
public class ItemsResource {
	ItemsService itemsService = new ItemsService();
	RestaurantsService resauratsService = new RestaurantsService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllItems() {
		Collection<Item> items = null;
		try {
			items = this.itemsService.getAll();
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError()
					.build();
		}
		
		return Response.ok(items)
						.build();
	}
	
	@Path("/id/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") Long id) {
		Item item = null;
		try {
			item = this.itemsService.getItemById(id);
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(item).build();
	}
	
	@GET
	@Path("/restaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemsForRestaurant(@QueryParam("id") Long id) {
		Collection<Item> items = null;
		try {
			items = this.itemsService.getItemsForRestaurantIf(id);
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(items).build();
	}
	
	@Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchItems( @QueryParam("type") String type,
								 @QueryParam("price") int price,
								 @QueryParam("name") String name,
								 @QueryParam("restaurant") String restaurantName) {
		Collection<Item> items = null;
		Item.ItemType itemType =  null;
		Restaurant restaurant = null;
		try {
			
			//items = itemsService.searchItems(itemType, price, name, restaurantName);
			items = this.itemsService.getAll();
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		if(type != null) {
			itemType = Item.ItemType.valueOf(type.toUpperCase());
			final Item.ItemType innerType = itemType;
			items = items.stream()
						 .filter((item)->item.getType().equals(innerType))
						 .collect(Collectors.toCollection(ArrayList::new));
		}
		
		if(price > 0) {
			items = items.stream()
						 .filter((item)->item.getPrice()==price)
						 .collect(Collectors.toCollection(ArrayList::new));
		}
		
		if(name != null) { 
			items = items.stream()
						 .filter((item)->item.getName().equals(name))
						 .collect(Collectors.toCollection(ArrayList::new));
		}
		
		if(restaurantName != null) {
			try {
				restaurant = this.resauratsService.getRestaurantByName(restaurantName);
			}catch(IOException e) {
				e.printStackTrace();
				return Response.serverError().build();
			}
			Long restaurantId = restaurant.getId();
			items = items.stream()
						 .filter((item)->item.getRestaurantId()==restaurantId)
						 .collect(Collectors.toCollection(ArrayList::new));
			
		}
		
		return Response.ok(items).build();
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertItem(Item item) {
		try {
			this.itemsService.addItem(item);
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError()
							.build();
		}
		
		return Response.status(Status.CREATED)
						.entity(item)
						.build();
	}
	
	@GET
	@Path("/top")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTop() {
		Collection<Item> topItems = null;
		try {
			topItems = this.itemsService.getTopTen();
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.status(Status.OK)
						.entity(topItems)
						.build();
	}
	
	@Path("/delete/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteItem(@PathParam("id") long id) {
		try {
			if(this.itemsService.removeItem(id)){
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
	public Response updateItem(Item item) {
		try {
			if(this.itemsService.updateItem(item)) {
				return Response.status(Status.OK)
								.entity(item)
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
