package com.revature.razangorm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.razang.utilities.ConnectionObject;
import com.revature.razangorm.models.Account;
import com.revature.razangorm.models.Customer;
import com.revature.razangorm.orm.ObjectRelationalMapper;
import com.revature.razangorm.orm.ObjectRelationalMapperImpl;

@ExtendWith (MockitoExtension.class)
public class ORMTest {
	@InjectMocks
	ObjectRelationalMapper test = new ObjectRelationalMapperImpl();

	@Mock
	private ConnectionObject connObj;

	@Mock
	private Statement st;

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
	public void findAll() throws SQLException {
		Customer customer = new Customer(1, "raza@gmail.com", new Date(1999-01-01),
        		"razaghulam", "10210101010", "12345");

		Connection jdbcConnection = Mockito.mock(Connection.class);
		Statement statement = Mockito.mock(Statement.class);
		ResultSet result = Mockito.mock(ResultSet.class);

		
		// Mockito.when (connObj.getConnection()).thenReturn(jdbcConnection);
		// Mockito.when (jdbcConnection.createStatement()).thenReturn(statement);
		// Mockito.when(result.next()).thenReturn(true).thenReturn(false);

		//Unfinished mocking
		// List<Object> obj = test.findAll(customer, "customer");
		// System.out.println(obj);
		
	}
	
	@Test
	public void testFindAllAccount() {
		Account account = new Account("123344556", 0.00, 1 );

		List<Object> accObjs = test.findAll(account, "BankAccount"); 
		System.out.println(accObjs);

		// List<Object> accObjs = test.findAll(account, "BankAccount"); 
		// System.out.println(accObjs);

	}
	
	@Test
	public void testUpate() {
		 Customer customer = new Customer(2, "razaghulam@gmail.com", new Date(1999-01-01),
	        		"razaghulam123", "10210101010", "12345");
		
		
		 assertNotNull(test.update(customer, "customer"));
	}
	
	@Test 
	public void testDelete() {
		Customer customer = new Customer(2, "razaghulam@gmail.com", new Date(1999-01-01),
        		"razaghulam123", "10210101010", "12345");
		assertNotNull(test.delete(customer, "customer"));
	}
}
