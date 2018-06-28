package org.arsenije.webproject.resources;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.arsenije.webproject.beans.Consumer;
import org.arsenije.webproject.beans.Delieverer;
import org.arsenije.webproject.beans.User;
import org.arsenije.webproject.services.ConsumerService;
import org.arsenije.webproject.services.DelievererService;
import org.arsenije.webproject.services.UsersService;


@Path("/users")
public class UsersResource {
	
	UsersService usersService = new UsersService();
	ConsumerService consumerService = new ConsumerService();
	DelievererService delievererService = new DelievererService();
	
	@Context
	HttpServletRequest request;
	
	@GET
	//@Secured({User.RoleEnum.ADMIN, User.RoleEnum.CONSUMER})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		
		Collection<User> users = null;
		
		try {
			users = usersService.getAll();
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(users).build();
		
	}
	
	@Path("/logged")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLoggedInUser() {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String username = Authentication.validateToken(authorizationHeader);
		
		User user = null;
		try { 
			user = this.usersService.getUser(username);
		}catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok(user).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertUser(User user) {
		Consumer consumer = new Consumer();
		try {
			usersService.addUser(user);
			consumer.setId(user.getId());
			consumerService.addConsumer(consumer);
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response
				.ok(user)
				.build();
	}
	
	@Path("/delieverer")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertDelieverer(User user) {
		Delieverer delieverer = new Delieverer();
		try {
			user.setRole(User.RoleEnum.DELIEVERER);
			usersService.addUser(user);
			delieverer.setId(user.getId());
			delievererService.addDelieverer(delieverer);
		}catch(IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response
				.ok(user)
				.build();
	}
	
	
	
}
