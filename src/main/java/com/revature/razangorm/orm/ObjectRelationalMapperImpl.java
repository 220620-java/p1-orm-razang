package com.revature.razangorm.orm;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.razang.utilities.ConnectionObject;


public class ObjectRelationalMapperImpl implements ObjectRelationalMapper {

	private ConnectionObject connObj = ConnectionObject.getConnectionUtil();
	private QueryMapper mapper = new QueryMapper(); 
	List<Object> objects = new ArrayList<>();
	
	@Override
	public  Object create(Object obj) {
		// TODO Auto-generated method stub
		try (Connection conn = connObj.getConnection()) {
			conn.setAutoCommit(false);
			String sql = mapper.createObject(obj, "customer"); 
			PreparedStatement st = conn.prepareStatement(sql);
			Field[] fields = mapper.getFields(obj.getClass()); 
			for (Field field: fields) {
				System.out.println(field);
			}
			
			
			
			
		} catch(SQLException e) {
			e.getStackTrace(); 
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Object findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object update(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object delete(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
