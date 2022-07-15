package com.revature.razangorm.models;

public class Account {
	
	private String account_no; 
	private double balance;
	private int customer_id;
	
	public Account(String account_no, double balance, int customer_id) {
		super();
		this.account_no = account_no;
		this.balance = balance;
		this.customer_id = customer_id;
	}
	
	public Account() {
		
	}
	
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	@Override
	public String toString() {
		return "Account [account_no=" + account_no + ", balance=" + balance + ", customer_id=" + customer_id + "]";
	} 
	
	
	
	
	
	

	
}
