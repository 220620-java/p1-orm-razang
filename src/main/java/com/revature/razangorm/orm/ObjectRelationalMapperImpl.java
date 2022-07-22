package com.revature.razangorm.orm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.revature.razangorm.utilities.ConnectionObject;
/**
 * 
 * @author razaghulam
 * @author Colby Tang
 */
public class ObjectRelationalMapperImpl implements ObjectRelationalMapper {

	private ConnectionObject connObj = ConnectionObject.getConnectionUtil();

	public ObjectRelationalMapperImpl () {}
	
	/** 
	 * @param obj
	 * @param s
	 * @return Object
	 * @author razaghulam
	 */
	@Override
	public Object create(Object obj, String s) {
		
		try (Connection conn = connObj.getConnection()) {
			
			conn.setAutoCommit(false);
			String sql = QueryMapper.createObject(obj,s); 
			Field[] fields = QueryMapper.getFields(obj.getClass());
			
			fields[0].setAccessible(true);
			
			String[] autoKeys = {fields[0].getName().toLowerCase()};
			PreparedStatement st = conn.prepareStatement(sql, autoKeys);
			
			for (int i = 1; i <fields.length; i++) {
				fields[i].setAccessible(true);
				st.setObject(i, fields[i].get(obj));
			}
			
			int rowsAdded = st.executeUpdate(); 
			ResultSet result = st.getGeneratedKeys();
			
			if (result.next() && rowsAdded == 1) {
				conn.commit();
			}else {
				conn.rollback();
				return null; 
			}
				
		} catch(SQLException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace(); 
		} 
		return obj;
	}

	/** 
	 * @param obj
	 * @param c
	 * @return Object
	 * @author razaghulam
	 */
	@Override
	public Object findByName(Object obj, String tableName) {
		try (Connection conn = connObj.getConnection()) {
			String sql = QueryMapper.findObjectByName(obj, tableName);
			if (sql == null) {
				System.out.println("No username annotation found!");
				return null;
			}

			Class<? extends Object> objClass = obj.getClass();

			Field[] fields = QueryMapper.getFields(objClass);
			
			Statement st = conn.createStatement();
			ResultSet result =st.executeQuery(sql);
			
			if (result.next()) {
				Object myObj = objClass.getConstructor().newInstance();
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					fields[i].set(myObj,result.getObject(fields[i].getName())); 
				}
				return myObj;
			}
		}catch (SQLException | NoSuchFieldException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return obj;
	}

	
	/** 
	 * @param obj
	 * @param c
	 * @return Object
	 * @author razaghulam
	 */
	@Override
	public Object findById(Object obj, String c) {
		try (Connection conn = connObj.getConnection()) {
			String sql = QueryMapper.findObjectById(obj, c); 
			Class<? extends Object> objClass = obj.getClass();

			Field[] fields = QueryMapper.getFields(objClass);
			
			Statement st = conn.createStatement();
			ResultSet result =st.executeQuery(sql);
			
			if  (result.next()) {
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					fields[i].set(obj,result.getObject(fields[i].getName())); 
				}
				return obj; 
			}else {
				return null; 
			}
		}catch (SQLException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/** 
	 * @param obj
	 * @param s
	 * @return List<Object>
	 * @author razaghulam
	 */
	@Override
	public List<Object> findAll(Class<? extends Object> objClass, String s) {
		List<Object> objects = new ArrayList<>();
		
		try (Connection conn = connObj.getConnection()) {
			String sql = QueryMapper.findAll(s); 
			
			Field[] fields = QueryMapper.getFields(objClass);

			Statement st = conn.createStatement(); 
			ResultSet result = st.executeQuery(sql); 
			
			while(result.next()) {
				Object myObj = objClass.getConstructor().newInstance();
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					fields[i].set(myObj,result.getObject(fields[i].getName())); 
				}
				objects.add(myObj); 
			}
		} catch(SQLException | IllegalArgumentException | IllegalAccessException | SecurityException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return objects;
	}

	
	/** 
	 * @param obj
	 * @param c
	 * @return Object
	 * @author razaghulam
	 */
	@Override
	public Object update(Object obj, String c) {
		try (Connection conn = connObj.getConnection()) {
			conn.setAutoCommit(false);
			String sql = QueryMapper.updateObject(obj, c); 
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

	public void updateField (String idName, int id, Map<String, Object> fields,  String tableName) {
		try (Connection conn = connObj.getConnection()) {
			List<String> keys = new ArrayList<>();
			for (String key : fields.keySet()) {
				keys.add(key);
			}

			conn.setAutoCommit(false);
			String sql = QueryMapper.updateObjectField(idName, id, keys, tableName); 
			PreparedStatement st = conn.prepareStatement(sql); 
			for (int i = 0; i < fields.size(); i++) {
				st.setObject(i+1, fields.get(keys.get(i)));
			}
			int rowUpdated = st.executeUpdate(); 

			if (rowUpdated == 1) {
				conn.commit();
			} else {
				conn.rollback();
			}
		}catch (SQLException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	
	/** 
	 * @param obj
	 * @param c
	 * @return Object
	 * @author razaghulam
	 */
	@Override
	public Object delete(Object obj, String c) {
		try (Connection conn = connObj.getConnection()) {
			conn.setAutoCommit(false);
			String sql = QueryMapper.deleteObject(obj, c); 
			
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

}
