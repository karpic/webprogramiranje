package org.arsenije.webproject.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.arsenije.webproject.beans.Item;
import org.arsenije.webproject.beans.Restaurant;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemsService {
	private ObjectMapper om;
	private File itemsFile;
	private RestaurantsService restaurantService;
	
	public ItemsService() {
		this.om = new ObjectMapper();
		this.itemsFile = new File("C:\\Users\\Arsenije\\source\\repos\\web\\NonMavenWebproject\\resources\\items.json");
		this.restaurantService = new RestaurantsService();
	}
	
	public Item getItemById(Long id) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Item> items = new ArrayList<Item>();
		items = getAll();
		
		return items.stream()
					.filter((item)->item.getId()==id)
					.findFirst()
					.orElse(null);
 	}
	
	public ArrayList<Item> getAll() throws JsonParseException, JsonMappingException, IOException{
		return om.readValue(itemsFile, om.getTypeFactory().constructCollectionLikeType(ArrayList.class, Item.class));
	}
	
	public Item getItem(Long id) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Item> items = new ArrayList<Item>();
		items = getAll();
		
		return items.stream()
				.filter((item)->item.getId() == id)
				.findFirst()
				.orElse(null);
	}
	
	public List<Item> getItemsForRestaurantIf(Long id) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Item> allItems = getAll();
		
		return allItems.stream()
						.filter((item)->item.getRestaurantId()==id)
						.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public List<Item> searchItems(Item.ItemType type, int price, String name, String restaurantName) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Item> allItems = getAll();
		
		Restaurant itemRestaurant = this.restaurantService.getRestaurantByName(restaurantName);
		
		return allItems.stream()
				.filter((item)->item.getType().equals(type))
				.filter((item)->item.getPrice()==price)
				.filter((item)->item.getName().equals(name))
				.filter((item)->item.getRestaurantId()==itemRestaurant.getId())
				.collect(Collectors.toCollection(ArrayList::new));
				
				
	}
	
	public boolean addItem(Item item) throws JsonParseException, JsonMappingException, IOException{
		if( getItem(item.getId()) != null) {
			return false;
		}
		
		ArrayList<Item> items = getAll();
		item.setId((long)(items.size() + 1));
		item.setDeleted(false);
		items.add(item);
		om.writeValue(itemsFile, items);
		return true;
	}
	
	public ArrayList<Item> getTopTen() throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Item> items = getAll();
		
		return items.stream()
					.sorted((i1, i2) -> i1.compareTo(i2))
					.limit(10)
					.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public boolean removeItem(long id) throws JsonParseException, JsonMappingException, IOException{
		Item item = null;
		ArrayList<Item> items = getAll();
		int itemIndex = IntStream.range(0, items.size())
				.filter(i -> items.get(i).getId().equals(id))
				.findFirst()
				.orElse(-1);
		if(itemIndex == -1) {
			return false;
		}
		item = items.get(itemIndex);
		item.setDeleted(true);
		items.set(itemIndex, item);
		om.writeValue(itemsFile, items);
		return true;
	}
	
	public boolean updateItem(Item item) throws JsonParseException, JsonMappingException, IOException{
		Item itemToUpdate = null;
		ArrayList<Item> items = getAll();
		int itemIndex = IntStream.range(0, items.size())
				.filter(i -> items.get(i).getId().equals(item.getId()))
				.findFirst()
				.orElse(-1);
		if(itemIndex == -1) {
			return false;
		}
		itemToUpdate = items.get(itemIndex);
		itemToUpdate.setDeleted(item.isDeleted());
		itemToUpdate.setDescription(item.getDescription());
		itemToUpdate.setName(item.getName());
		itemToUpdate.setPrice(item.getPrice());
		itemToUpdate.setQuantity(item.getQuantity());
		itemToUpdate.setRestaurantId(item.getRestaurantId());
		itemToUpdate.setTimesOrdered(item.getTimesOrdered());
		itemToUpdate.setType(item.getType());
		
		items.set(itemIndex, itemToUpdate);
		om.writeValue(itemsFile, items);
		return true;
	}
	
}
