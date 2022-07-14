package com.revature.razang.utilities;

public class Generator {

	public String randomGerenator() {
		
		long number = (long) Math.floor(Math.random() * 9000000000000L)
				+ 1000000000000L;
		String random = String.valueOf(number); 
		return random; 
	}

}
