package com.revature.razangorm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.revature.razangorm.models.Customer;
import com.revature.razangorm.orm.ObjectRelationalMapper;
import com.revature.razangorm.orm.ObjectRelationalMapperImpl;

public class ORMTest {
	
	ObjectRelationalMapper test = new ObjectRelationalMapperImpl(); 

//    @Test
//    public void updateObjectByEmail () {
        Customer customer = new Customer(1, "raza@gmail.com", new Date(1999-01-01),
        		"razaghulam", "10210101010", "12345");
//        try {
//		    String actual = QueryMapper.updateObject(customer, "raza.ghulam@gmail.com", "customer");
//            String expected = "update customer set email=? where customer_id =?";
//            assertEquals(expected, actual);
//        }
//        catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        catch (SecurityException e) {
//            e.printStackTrace();
//        }
//    }
	@Test
	public void create() {
		Customer customer = new Customer(1, "raza@gmail.com", new Date(1999-01-01),
        		"razaghulam", "10210101010", "12345");
        Object c = test.create(customer);
		assertEquals(customer, c); 

	}
}
