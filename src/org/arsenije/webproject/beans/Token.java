package org.arsenije.webproject.beans;


public class Token {
	private User.RoleEnum role;
	private String username;
	private String token;
	
	
	
	public Token() {
		super();
	}
	public User.RoleEnum getRole() {
		return role;
	}
	public void setRole(User.RoleEnum role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
