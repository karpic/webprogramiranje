package org.arsenije.webproject.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.arsenije.webproject.beans.Consumer;
import org.arsenije.webproject.beans.Restaurant;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestaurantsService {
	private ObjectMapper om;
	private File restaurantFile;
	private ConsumerService consumerService = new ConsumerService();
	
	public RestaurantsService() {
		this.om = new ObjectMapper();
		this.restaurantFile = new File("C:\\Users\\Arsenije\\source\\repos\\web\\NonMavenWebproject\\resources\\restaurants.json");
	}
	
	public ArrayList<Restaurant> getAll() throws JsonParseException, JsonMappingException, IOException{
		return om.readValue(restaurantFile, om.getTypeFactory().constructCollectionLikeType(ArrayList.class, Restaurant.class));
	}
	
	public Restaurant getRestaurant(Long id) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		restaurants = getAll();
		
		return restaurants.stream()
				.filter((restaurant)->restaurant.getId() == id)
				.findFirst()
				.orElse(null);
	}
	
	public Restaurant getRestaurantByName(String name) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		restaurants = getAll();
		
		return restaurants.stream()
				.filter((restaurant)->restaurant.getName().equals(name))
				.findFirst()
				.orElse(null);
	}
	
	public boolean addRestaurant(Restaurant restaurant) throws JsonParseException, JsonMappingException, IOException{
		if( getRestaurant(restaurant.getId()) != null) {
			return false;
		}
		
		ArrayList<Restaurant> restaurants = getAll();
		restaurant.setId((long)restaurants.size() + 1);
		restaurant.setDeleted(false);
		restaurant.setDishes(new ArrayList<Long>());
		restaurant.setDrinks(new ArrayList<Long>());
		
		restaurants.add(restaurant);
		om.writeValue(restaurantFile, restaurants);
		return true;
	}
	
	public List<Restaurant> getRestaurantsPaginated(int start, int size)  throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Restaurant> allRestaurants = getAll();
		return allRestaurants.subList(start, start + size);
	}
	
	public List<Restaurant> getRestaurantsByCategory(Restaurant.RestaurantCategory category) throws JsonParseException, JsonMappingException, IOException {
		ArrayList<Restaurant> allRestaurants = getAll();
		
		return allRestaurants.stream()
							.filter((restaurant)->restaurant.getCategory().equals(category))
							.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public List<Restaurant> getSavedRestaurants(Long userId) throws JsonParseException, JsonMappingException, IOException{
		Consumer consumer = this.consumerService.getConsumer(userId);
		ArrayList<Long> savedRestaurantIds = (ArrayList<Long>)consumer.getFavoriteRestaurants();
		
		ArrayList<Restaurant> restaurants = getAll();
		ArrayList<Restaurant> returnRes = new ArrayList<Restaurant>();
		
		for(Long id: savedRestaurantIds) {
			restaurants.stream()
						.filter((r)->r.getId().equals(id))
						.forEach((r) ->returnRes.add(r));
		}
		return returnRes;
	}
 	
	public boolean removeRestaurant(long id) throws JsonParseException, JsonMappingException, IOException{
		Restaurant restaurant  = null;
		ArrayList<Restaurant> restaurants = getAll();
		int itemIndex = IntStream.range(0, restaurants.size())
				.filter(i -> restaurants.get(i).getId().equals(id))
				.findFirst()
				.orElse(-1);
		if(itemIndex == -1) {
			return false;
		}
		restaurant = restaurants.get(itemIndex);
		restaurant.setDeleted(true);
		restaurants.set(itemIndex, restaurant);
		om.writeValue(restaurantFile, restaurants);
		return true;
	}
	
	public boolean updateRestaurant(Restaurant restaurant) throws JsonParseException, JsonMappingException, IOException{
		Restaurant restaurantToUpdate  = null;
		ArrayList<Restaurant> restaurants = getAll();
		int itemIndex = IntStream.range(0, restaurants.size())
				.filter(i -> restaurants.get(i).getId().equals(restaurant.getId()))
				.findFirst()
				.orElse(-1);
		if(itemIndex == -1) {
			return false;
		}
		restaurantToUpdate = restaurants.get(itemIndex);
		
		restaurantToUpdate.setAddress(restaurant.getAddress());
		restaurantToUpdate.setCategory(restaurant.getCategory());
		restaurantToUpdate.setDeleted(restaurant.isDeleted());
		restaurantToUpdate.setName(restaurant.getName());
		restaurantToUpdate.setDishes(restaurant.getDishes());
		restaurantToUpdate.setDrinks(restaurant.getDrinks());
		
		
		restaurants.set(itemIndex, restaurantToUpdate);
		om.writeValue(restaurantFile, restaurants);
		return true;
	}

}
