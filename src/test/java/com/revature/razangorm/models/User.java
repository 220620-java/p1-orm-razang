package com.revature.razangorm.models;
import java.sql.Date;

import com.revature.razangorm.annotations.Id;
import com.revature.razangorm.annotations.Username;
import com.revature.razangorm.utilities.ORMUtils;

/**
 * @author Raza Ghulam
 * @author Colby Tang
 */
public class User {
	@Id
	private int userId; 
	@Username
	private String username;
	private Date birthDate; 
	private String email; 
	private String phone; 
	private String password;
	private byte[] salt;

	public User () { }

    public User(int userId, String username, Date birthDate, String email, String phone, String password) {
		this.userId = userId;
		this.username = username;
		this.birthDate = birthDate;
		this.email = email;
		this.phone = phone;
		
		// Encrypt password
		this.salt = ORMUtils.generateSalt();
		this.password = ORMUtils.generateEncryptedPassword(password, this.salt);
	}
	
	public User(int userId, String username, Date birthDate, String email, String phone, String password, byte[] salt) {
		this.userId = userId;
		this.username = username;
		this.birthDate = birthDate;
		this.email = email;
		this.phone = phone;
		this.salt = salt;
		this.password = password;
	}
	
	/** 
	 * @return String
	 */
	@Override
	public String toString() {
		String retString = "UserId={0}, Username={1}, BirthDate={2}, Email={3}, Phone={4}, Password={5}, Salt={6}";
		retString = String.format(retString, getUserId(), getUsername(), getBirthDate(), getEmail(), getPhone(), getPassword(), getSalt());
		return retString;
	}

	/** 
	 * @return int
	 */
	public int getUserId() {
		return userId;
	}
	
	/** 
	 * @param customer_id
	 */
	public void setUserId(int customer_id) {
		this.userId = customer_id;
	}
	
	/** 
	 * @return String
	 */
	public String getUsername() {
		return username;
	}
	
	/** 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/** 
	 * @return Date
	 */
	public Date getBirthDate() {
		return birthDate;
	}
	
	/** 
	 * @param birthDate
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	/** 
	 * @return String
	 */
	public String getEmail() {
		return email;
	}
	
	/** 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/** 
	 * @return String
	 */
	public String getPhone() {
		return phone;
	}
	
	/** 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/** 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	
	/** 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/** 
	 * @return byte[]
	 */
	public byte[] getSalt() {
        return salt;
    }

	/** 
	 * @param salt
	 */
	public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
