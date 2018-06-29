package org.arsenije.webproject.resources;

import java.io.IOException;
import java.util.Collection;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.arsenije.webproject.beans.Order;
import org.arsenije.webproject.beans.User;
import org.arsenije.webproject.services.ConsumerService;
import org.arsenije.webproject.services.DelievererService;
import org.arsenije.webproject.services.OrdersService;
import org.arsenije.webproject.services.UsersService;

@Path("/orders")
public class OrdersResource {
	
	OrdersService ordersService = new OrdersService();
	ConsumerService consumerService = new ConsumerService();
	UsersService userService = new UsersService();
	DelievererService delievererService = new DelievererService();
	@Context
	HttpServletRequest request;
	
	@GET
	//@Secured({User.RoleEnum.ADMIN, User.RoleEnum.CONSUMER})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		
		Collection<Order> orders = null;
		
		try {
			orders = ordersService.getAll();
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(orders).build();
		
	}
	
	@Path("/myorders")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyOrders() {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String username = Authentication.validateToken(authorizationHeader);
		
		Collection<Order> orders = null;
		try {
			orders = ordersService.getMyOrders(username);
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(orders).build();
	}
	
	@Path("/mydelieveries")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyDeliveries() {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String username = Authentication.validateToken(authorizationHeader);
		
		Collection<Order> orders = null;
		try {
			orders = ordersService.getMyDelieveries(username);
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(orders).build();
	}
	
	@Path("/delievered")
	@POST
	public Response deliever(@QueryParam("id") Long id) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String username = Authentication.validateToken(authorizationHeader);
		User user = null;
		
		try {
			user = userService.getUser(username);
			this.ordersService.changeOrderToDelievered(id);
			this.delievererService.removeOrderFromList(user.getId(), id);
			this.delievererService.changeDelievererStatus(user.getId(), false);
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok().build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertOrder(Order order) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String username = Authentication.validateToken(authorizationHeader);
		User user = null;
		
		try {
			user = userService.getUser(username);
			ordersService.addOrder(order, username);
			consumerService.addOrderToUser(user.getId(), order.getId());
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(order).build();
	}
	
	@Path("/take")
	@POST
	public Response takeOrder(@QueryParam("id") Long id) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String username = Authentication.validateToken(authorizationHeader);
		User user = null;
		
		try {
			user = this.userService.getUser(username);
			this.ordersService.takeOrder(id, username);
			this.delievererService.addOrderToDeliever(user.getId(), id);
			this.delievererService.changeDelievererStatus(user.getId(), true);
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok().build();
	}
}
