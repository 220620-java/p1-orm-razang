package com.revature.razangorm.orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.raza.utility.ConnectionObject;

/**
 * 
 * @author razaghulam
 *
 */
public class ObjectRelationalMapperImpl implements ObjectRelationalMapper {

	private ConnectionObject connObj = ConnectionObject.getConnectionUtil();
	private QueryMapper mapper = new QueryMapper(); 
	
	
	@Override
	public  Object create(Object obj, String s) {
		
		
		try (Connection conn = connObj.getConnection()) {
			
			conn.setAutoCommit(false);
			String sql = mapper.createObject(obj,s); 
			Field[] fields = mapper.getFields(obj.getClass());
			
			fields[0].setAccessible(true);
			
			String[] autoKeys = {fields[0].getName()};
			PreparedStatement st = conn.prepareStatement(sql, autoKeys);
			
			for (int i = 1; i <fields.length; i++) {
				fields[i].setAccessible(true);
				st.setObject(i, fields[i].get(obj).toString().toLowerCase());				
			}
			
			int rowsAdded = st.executeUpdate(); 
			ResultSet result = st.getGeneratedKeys();
			
			if (result.next() && rowsAdded == 1) {
				conn.commit();
			}else {
				conn.rollback();
				return null; 
			}
				
		} catch(SQLException e) {
			e.getStackTrace(); 
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}

	@Override
	public List<Object> findAll(Object obj, String s) {
		// TODO Auto-generated method stub
		List<Object> objects = new ArrayList<>();
		
		try (Connection conn = connObj.getConnection()) {
			String sql = mapper.findAll(s); 
			
			Field[] fields = mapper.getFields(obj.getClass());

			Statement st = conn.createStatement(); 
			ResultSet result = st.executeQuery(sql); 
			
			while(result.next()) {
				Object myObj = obj.getClass().newInstance();
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					fields[i].set(myObj,result.getObject(fields[i].getName())); 
				}
				objects.add(myObj); 
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objects;
	}

	@Override
	public Object update(Object obj, String c) {
		// TODO Auto-generated method stub
		try (Connection conn = connObj.getConnection()) {
			conn.setAutoCommit(false);
			String sql = mapper.updateObject(obj, c); 
			PreparedStatement st = conn.prepareStatement(sql); 
			
			int rowUpdated = st.executeUpdate(); 
			if (rowUpdated == 1) {
				conn.commit();
			} else {
				conn.rollback();
			}
		}catch (SQLException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Object delete(Object obj, String c) {
		// TODO Auto-generated method stub
		try (Connection conn = connObj.getConnection()) {
			conn.setAutoCommit(false);
			@SuppressWarnings("static-access")
			String sql = mapper.deleteObject(obj, c); 
			
			PreparedStatement st = conn.prepareStatement(sql); 
			int rowAffected = st.executeUpdate(); 
			if (rowAffected == 1) {
				conn.commit();
			}else {
				conn.rollback();
				return null; 
			}	
			
		}catch(SQLException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Object findByName(Object obj, String c) {
		// TODO Auto-generated method stub
		try (Connection conn = connObj.getConnection()) {
			String sql = mapper.findObjectByName(obj, c); 
			
			Statement st = conn.createStatement();
			ResultSet result =st.executeQuery(sql);
			
			if  (result.next()) {
				return obj; 
			}else {
				return null; 
			}
		}catch (SQLException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Object findById(Object obj, String c) {
		// TODO Auto-generated method stub
		try (Connection conn = connObj.getConnection()) {
			String sql = mapper.findObjectById(obj, c); 
			
			Statement st = conn.createStatement();
			ResultSet result =st.executeQuery(sql);
			
			if  (result.next()) {
				return obj; 
			}else {
				return null; 
			}
		}catch (SQLException | SecurityException e) {
			e.printStackTrace();
		}
		return obj;
	}
	

	
	

}
