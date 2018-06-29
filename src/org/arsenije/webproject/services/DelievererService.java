package org.arsenije.webproject.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;
import org.arsenije.webproject.beans.Delieverer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DelievererService {
	private ObjectMapper om;
	private File delievererFile;
	
	public DelievererService() {
		this.om = new ObjectMapper();
		this.delievererFile = new File("C:\\Users\\Arsenije\\source\\repos\\web\\NonMavenWebproject\\resources\\delieverers.json");
	}
	
	public ArrayList<Delieverer> getAll() throws JsonParseException, JsonMappingException, IOException{
		return om.readValue(delievererFile, om.getTypeFactory().constructCollectionLikeType(ArrayList.class, Delieverer.class));
	}
	
	public Delieverer getDelieverer(Long id) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<Delieverer> delieverers = new ArrayList<Delieverer>();
		delieverers = getAll();
		
		return delieverers.stream()
				.filter((d)->d.getId() == id)
				.findFirst()
				.orElse(null);
	}
	
	public boolean addDelieverer(Delieverer delieverer) throws JsonParseException, JsonMappingException, IOException{
		if( getDelieverer(delieverer.getId()) != null) {
			return false;
		}
		
		ArrayList<Delieverer> delieveres = getAll();
		//consumer.setId((long)(consumers.size() + 1));
		delieverer.setOrdersToDeliever(new ArrayList<Long>());
		delieverer.setActive(false);
		delieveres.add(delieverer);
		om.writeValue(delievererFile, delieveres);
		return true;
	}
	
	
	
	public boolean addOrderToDeliever(Long userId, Long orderId) throws JsonParseException, JsonMappingException, IOException{
		Delieverer delieverer = null;
		ArrayList<Delieverer> delieverers = getAll();
		ArrayList<Long> delievererOrders = new ArrayList<Long>();
		int delievererIndex = IntStream.range(0, delieverers.size())
				.filter(c -> delieverers.get(c).getId().equals(userId))
				.findFirst()
				.orElse(-1);
		if(delievererIndex == -1) {
			return false;
		}
		
		delieverer = delieverers.get(delievererIndex);
		delievererOrders = (ArrayList<Long>)delieverer.getOrdersToDeliever();
		delievererOrders.add(orderId);
		delieverer.setOrdersToDeliever(delievererOrders);
		
		delieverers.set(delievererIndex, delieverer);
		om.writeValue(delievererFile, delieverers);
		return true;
	}
	
	public boolean removeOrderFromList(Long userId, Long orderId) throws JsonParseException, JsonMappingException, IOException{
		Delieverer delieverer = null;
		ArrayList<Delieverer> delieverers = getAll();
		ArrayList<Long> delievererOrders = new ArrayList<Long>();
		int delievererIndex = IntStream.range(0, delieverers.size())
				.filter(c -> delieverers.get(c).getId().equals(userId))
				.findFirst()
				.orElse(-1);
		if(delievererIndex == -1) {
			return false;
		}
		
		delieverer = delieverers.get(delievererIndex);
		delievererOrders = (ArrayList<Long>)delieverer.getOrdersToDeliever();
		delievererOrders.remove(orderId);
		delieverer.setOrdersToDeliever(delievererOrders);
		
		delieverers.set(delievererIndex, delieverer);
		om.writeValue(delievererFile, delieverers);
		return true;
	}
	
	public boolean changeDelievererStatus(Long id, boolean status) throws JsonParseException, JsonMappingException, IOException{
		Delieverer delieverer = null;
		ArrayList<Delieverer> delieverers = getAll();
		ArrayList<Long> delievererOrders = new ArrayList<Long>();
		int delievererIndex = IntStream.range(0, delieverers.size())
				.filter(c -> delieverers.get(c).getId().equals(id))
				.findFirst()
				.orElse(-1);
		if(delievererIndex == -1) {
			return false;
		}
		
		delieverer = delieverers.get(delievererIndex);
		
		delieverer.setActive(status);
		
		delieverers.set(delievererIndex, delieverer);
		om.writeValue(delievererFile, delieverers);
		return true;
	}


}
