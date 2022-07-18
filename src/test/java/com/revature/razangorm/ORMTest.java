package com.revature.razangorm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.revature.razangorm.models.Account;
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
        Object c = test.create(customer, "customer");
		assertEquals(customer, c); 

	}
	
	@Test 
	public void findAll() {
		Customer customer = new Customer(1, "raza@gmail.com", new Date(1999-01-01),
        		"razaghulam", "10210101010", "12345");
		List<Object> obj = test.findAll(customer, "customer");
		System.out.println(obj);
		
	}
	
	@Test
	public void testFindAllAccount() {
		Account account = new Account("123344556", 0.00, 1 );
		List<Object> accObjs = test.findAll(account, "BankAccount"); 
		System.out.println(accObjs);
		
	}
}
