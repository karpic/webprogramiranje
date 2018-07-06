package org.arsenije.webproject.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.arsenije.webproject.beans.Order;
import org.arsenije.webproject.resources.Authentication;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrdersService {
	private ObjectMapper om;
	private File ordersFile;

	
	public OrdersService() {
		this.om = new ObjectMapper();
		this.ordersFile = new File(this.getClass().getClassLoader().getResource("/resource/orders.json").getPath());
	}
	
	public ArrayList<Order> getAll() throws JsonParseException, JsonMappingException, IOException{
		return om.readValue(ordersFile, om.getTypeFactory().constructCollectionLikeType(ArrayList.class, Order.class));
	}
	
	public Order getOrder(Long id) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Order> orders = new ArrayList<Order>();
		orders = getAll();
		
		return orders.stream()
				.filter((order)->order.getId() == id)
				.findFirst()
				.orElse(null);
	}
	
	public List<Order> getMyOrders(String username) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Order> orders = new ArrayList<Order>();
		orders = getAll();
		
		return orders.stream()
					 .filter((order)->order.getUserUsername().equals(username))
					 .collect(Collectors.toCollection(ArrayList::new));
		
	}
	
	public List<Order> getMyDelieveries(String username) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Order> orders = new ArrayList<Order>();
		orders = getAll();
		
		return orders.stream()
					 .filter((order)->order.getDelievererUsername().equals(username))
					 .collect(Collectors.toCollection(ArrayList::new));
		
	}
	
	public boolean addOrder(Order order, String username) throws JsonParseException, JsonMappingException, IOException{
		
		
		if( getOrder(order.getId()) != null) {
			return false;
		}
		
		ArrayList<Order> orders = getAll();
		order.setId((long)(orders.size() + 1));
		order.setDeleted(false);
		order.setDelievererUsername("nedodeljen");
		order.setDateOrdered(new Date());
		order.setStatus(Order.OrderStatus.ORDERED);
		order.setUserUsername(username);
		orders.add(order);
		om.writeValue(ordersFile, orders);
		return true;
	}
	
	public boolean takeOrder(Long id, String username) throws JsonParseException, JsonMappingException, IOException{
		Order order = null;
		ArrayList<Order> orders = getAll();
		int itemIndex = IntStream.range(0, orders.size())
				.filter(i -> orders.get(i).getId().equals(id))
				.findFirst()
				.orElse(-1);
		if(itemIndex == -1) {
			return false;
		}
		
		order = orders.get(itemIndex);
		order.setStatus(Order.OrderStatus.DELIEVERING);
		order.setDelievererUsername(username);
		
		orders.set(itemIndex, order);
		om.writeValue(ordersFile, orders);
		return true;
	}
	
	public boolean changeOrderToDelievered(Long id) throws JsonParseException, JsonMappingException, IOException{
		Order order = null;
		ArrayList<Order> orders = getAll();
		int itemIndex = IntStream.range(0, orders.size())
				.filter(i -> orders.get(i).getId().equals(id))
				.findFirst()
				.orElse(-1);
		if(itemIndex == -1) {
			return false;
		}
		
		order = orders.get(itemIndex);
		order.setStatus(Order.OrderStatus.DELIEVERED);
		orders.set(itemIndex, order);
		om.writeValue(ordersFile, orders);
		return true;
	}
}
