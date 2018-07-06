package org.arsenije.webproject.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

import org.arsenije.webproject.beans.User;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UsersService {
	
	private ObjectMapper om;
	private File usersFile;
	
	public UsersService() {
		this.om = new ObjectMapper();
		this.usersFile = new File(this.getClass().getClassLoader().getResource("/resource/users.json").getPath());
	}
	
	public User getUser(String username) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<User> users = new ArrayList<User>();
		users = getAll();
		
		return users.stream()
				.filter((user)->user.getUsername().equals(username))
				.findFirst()
				.orElse(null);
	}
	
	public User getUserById(Long id) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<User> users = new ArrayList<User>();
		users = getAll();
		
		return users.stream()
				.filter((user)->user.getId()==id)
				.findFirst()
				.orElse(null);
	}
	
	public ArrayList<User> getAll() throws JsonParseException, JsonMappingException, IOException{
		return om.readValue(usersFile, om.getTypeFactory().constructCollectionType(ArrayList.class, User.class));
	}
	
	
	public boolean addUser(User user) throws JsonParseException, JsonMappingException, IOException{
		if( getUser(user.getUsername()) != null) {
			return false;
		}
		
		ArrayList<User> users = getAll();
		user.setId((long)(users.size() + 1));
		user.setRole(User.RoleEnum.CONSUMER);
		
		users.add(user);
		om.writeValue(usersFile, users);
		return true;
	}
	
	public boolean addDelieverer(User user) throws JsonParseException, JsonMappingException, IOException{
		if( getUser(user.getUsername()) != null) {
			return false;
		}
		
		ArrayList<User> users = getAll();
		user.setId((long)(users.size() + 1));
		
		users.add(user);
		om.writeValue(usersFile, users);
		return true;
	}
	
	public boolean updateUser(User user) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<User> users = getAll();
		
		int userIndex = IntStream.range(0, users.size())
								.filter(i -> users.get(i).getId().equals(user.getId()))
								.findFirst()
								.orElse(-1);
		if(userIndex == -1) {
			return false;
		}
		
		users.set(userIndex, user);
		om.writeValue(usersFile, users);
		return true;
	}
	
	public boolean changeRole(User.RoleEnum role, Long id) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<User> users = getAll();
		User user = null;
		int userIndex = IntStream.range(0, users.size())
								.filter(i -> users.get(i).getId().equals(id))
								.findFirst()
								.orElse(-1);
		if(userIndex == -1) {
			return false;
		}
		
		user = users.get(userIndex);
		user.setRole(role);
		users.set(userIndex, user);
		om.writeValue(usersFile, users);
		
		//TO DO proveriti da li je consumer, ako nije dodati u consumers.json
		//TO DO 2 proveriti da li je delieverer, ako nije dodati u delieverers.json
		return true;
		
	}
}
