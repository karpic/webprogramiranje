package org.arsenije.webproject.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

import org.arsenije.webproject.beans.Consumer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsumerService {
	private ObjectMapper om;
	private File consumersFile;
	
	public ConsumerService() {
		this.om = new ObjectMapper();
		this.consumersFile = new File("C:\\Users\\Arsenije\\source\\repos\\web\\NonMavenWebproject\\resources\\consumers.json");
	}
	
	public ArrayList<Consumer> getAll() throws JsonParseException, JsonMappingException, IOException{
		return om.readValue(consumersFile, om.getTypeFactory().constructCollectionLikeType(ArrayList.class, Consumer.class));
	}
	
	public Consumer getConsumer(Long id) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Consumer> consumers = new ArrayList<Consumer>();
		consumers = getAll();
		
		return consumers.stream()
				.filter((consumer)->consumer.getId() == id)
				.findFirst()
				.orElse(null);
	}
	
	public boolean addConsumer(Consumer consumer) throws JsonParseException, JsonMappingException, IOException{
		if( getConsumer(consumer.getId()) != null) {
			return false;
		}
		
		ArrayList<Consumer> consumers = getAll();
		//consumer.setId((long)(consumers.size() + 1));
		consumer.setFavoriteRestaurants(new ArrayList<Long>());
		consumer.setOrders(new ArrayList<Long>());
		
		consumers.add(consumer);
		om.writeValue(consumersFile, consumers);
		return true;
	}
	
	public boolean addOrderToUser(Long userId, Long orderId) throws JsonParseException, JsonMappingException, IOException{
		Consumer consumer = null;
		ArrayList<Consumer> consumers = getAll();
		ArrayList<Long> consumerOrders = new ArrayList<Long>();
		int consumerIndex = IntStream.range(0, consumers.size())
				.filter(c -> consumers.get(c).getId().equals(userId))
				.findFirst()
				.orElse(-1);
		if(consumerIndex == -1) {
			return false;
		}
		
		consumer = consumers.get(consumerIndex);
		consumerOrders = (ArrayList<Long>)consumer.getOrders();
		consumerOrders.add(orderId);
		consumer.setOrders(consumerOrders);
		
		consumers.set(consumerIndex, consumer);
		om.writeValue(consumersFile, consumers);
		return true;
	}
	
	public boolean addRestaurantToUser(Long userId, Long restaurantId) throws JsonParseException, JsonMappingException, IOException{
		Consumer consumer = null;
		ArrayList<Consumer> consumers = getAll();
		ArrayList<Long> consumerRestaurants = new ArrayList<Long>();
		int consumerIndex = IntStream.range(0, consumers.size())
				.filter(c -> consumers.get(c).getId().equals(userId))
				.findFirst()
				.orElse(-1);
		if(consumerIndex == -1) {
			return false;
		}
		
		consumer = consumers.get(consumerIndex);
		consumerRestaurants = (ArrayList<Long>)consumer.getFavoriteRestaurants();
		consumerRestaurants.add(restaurantId);
		consumer.setFavoriteRestaurants(consumerRestaurants);
		
		consumers.set(consumerIndex, consumer);
		om.writeValue(consumersFile, consumers);
		return true;
	}
}
