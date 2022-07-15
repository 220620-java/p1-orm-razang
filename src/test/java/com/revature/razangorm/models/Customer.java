package com.revature.razangorm.models;
import java.sql.Date;

import com.revature.razangorm.annotations.Id;
import com.revature.razangorm.annotations.Username;

public class Customer {
	@Id
	private int customer_id; 
	@Username
	private String username; 
	private Date birthDate; 
	private String email; 
	private String phone; 
	private String passwd;
	
	
	public Customer(int customer_id, String username, 
			Date birthDate, String email, String phone, String passwd) {
		super();
		this.customer_id = customer_id;
		this.username = username;
		this.birthDate = birthDate;
		this.email = email;
		this.phone = phone;
		this.passwd = passwd;
	}
	
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "\n	customer_id  " + customer_id + "\n	username  " + username + "\n	birthDate  " + birthDate
				+ "\n	email  " + email + "\n	phone  " + phone + "\n\tpassword " + passwd ;
	}


	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


}
