package com.revature.razangorm.models;

public class Customer {
	
	private int customer_id; 
	private String email; 
	private String username;
	
	
	
	public Customer(int customer_id, String email, String username) {
		super();
		this.customer_id = customer_id;
		this.email = email;
		this.username = username;
	}
	
	
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	} 

}
