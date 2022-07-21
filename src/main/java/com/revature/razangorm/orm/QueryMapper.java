package com.revature.razangorm.orm;

// java imports
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.revature.razangorm.annotations.Id;
import com.revature.razangorm.annotations.ORMIgnore;
import com.revature.razangorm.annotations.Subclass;
import com.revature.razangorm.annotations.Username; 

/** This class mainly returns string queries from the class object. 
 * @author Team Razang
 */
public class QueryMapper {

    // Call method example: String sqlQuery = ObjectRelationMapper.insertObject(student, "users");
    // Returns something like
    // INSERT INTO users (id,firstName,lastName,username,email,userType,salt,major,gpa) VALUES (?,?,?,?,?,?,?,?,?);
    /**
     * Takes an object and returns an SQL insert statement to insert into database.
     * @author Colby Tang
     */
    public static String createObject (Object obj, String tableName) {
        Class<?> objClass = obj.getClass();
        Field[] fields = getFields(objClass);
        
        // Create the insert fields
        String insertQuery = "INSERT INTO " + tableName + " (";
        String valuesQuery = " VALUES (default, ";
        StringJoiner insertJoiner = new StringJoiner(",");
        StringJoiner valuesJoiner = new StringJoiner(",");
        Stream<Field> fieldsStream = Arrays.stream(fields);
        fieldsStream.forEach(
            field -> {
                // Add each field separated by a comma
                insertJoiner.add(field.getName().toLowerCase());

                // Add ? for each field in fields
//                valuesJoiner.add("?");
            }
        );
        
        insertQuery += insertJoiner.toString();
        insertQuery += ")";
        for (int i = 1; i < fields.length; i++) {
        	valuesQuery += "?, "; 
        }
        valuesQuery = valuesQuery.replaceAll(", $", " "); 
        valuesQuery +=  ");";
        return insertQuery += valuesQuery;
    }

    /**
     * 
     * @param obj passed by caller
     * @param eMail: new email
     * @param tableName: table where email will be updated
     * @return sql query
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @author razaghulam
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    // Expected sample output: returnQuery = "update Customer set email=? where customer_id=?";
	public static String updateObject(Object obj, String tableName) throws NoSuchFieldException, 
	SecurityException, IllegalArgumentException, IllegalAccessException {

    	Class<?> objClass = obj.getClass();
    	
    	Field[] fields = objClass.getDeclaredFields(); 
    	String updateFields = new String();
    	String id = null, val = null; 
    	
    	for (Field field: fields) {
    		field.setAccessible(true);
    		if (field.isAnnotationPresent(Id.class)) {
    			id = field.getName(); 
    			val =  field.get(obj).toString(); 
    		} else if (!field.isAnnotationPresent(Id.class)) {
    			updateFields = updateFields.concat(field.getName().toString()).concat("='"); 
    			if (field.get(obj) == null) {
    				updateFields = updateFields.concat(null).concat("', "); 
    			} else {
    				updateFields = updateFields.concat(field.get(obj).toString()).concat("',");

    			}
    		}
    	}
    	// Remove the trailing comma. 
		updateFields = updateFields.replaceAll(",$", " ");

    	String returnQuery = "update " + tableName +  " set " + updateFields.toString() 
    	+ " where " + id + " =" + val; 
    	
    	return returnQuery; 
    }

    public static String updateObjectField(String idName, int id, List<String> fields,  String tableName) {
    	String returnQuery = "UPDATE " + tableName + " SET ";
        StringJoiner joiner = new StringJoiner(",");
        for (String key : fields) {
            joiner.add(key + "=?");
        }
        returnQuery += joiner.toString();
        returnQuery += " WHERE " + idName + "=" + id;
    	return returnQuery; 
    }
    
    /**
     * 
     * @param tableName
     * @return
     */
    public static String findAll(String tableName) {
    	String returnQuery = "select * from " + tableName; 
		return returnQuery;
    }

    
    /** Get a value from a table where the id matches
     * @param idName
     * @param id
     * @param fieldName
     * @param tableName
     * @return String
     */
    public static String getValueById (String idName, int id, String fieldName, String tableName) {
        String returnQuery = "SELECT " + fieldName + " FROM " + tableName + " WHERE " + idName + "=" + id;
        return returnQuery;
    }

    /**
     * 
     * @param obj
     * @param tableName
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @author razaghulam
     */
    
    // Expected sample output: returnQuery  "delete from Customer where  customer_id = val";
    public static String deleteObject(Object obj, String tableName) throws NoSuchFieldException, SecurityException {
    	Class<?> objClass = obj.getClass();
    	String id = null, val=null; 
    	Field[] fields = objClass.getDeclaredFields(); 
    	for (Field field: fields) {
    		if (field.isAnnotationPresent(Id.class)) {
    			field.setAccessible(true);
    			id = field.getName(); 
    			try {
					val = field.get(obj).toString();
					break; 
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    		}
    	}
    	String returnQuery = "delete from " + tableName + " where " + id + " =" + val; 
		return returnQuery;
    }
   
    
    /** Returns the fields of a class and its superclasses
     * @author Colby Tang
     */
    public static Field[] getFields (Class<?> objClass) {
        Field[] fields = objClass.getDeclaredFields();
        
        // If class is a subclass, get the superclass fields too
        while (objClass.isAnnotationPresent(Subclass.class)) {
            objClass = objClass.getSuperclass();
            Field[] superFields = objClass.getDeclaredFields();
            fields = Stream.concat(Arrays.stream(superFields), Arrays.stream(fields))
                    .toArray(Field[]::new);
        }
        
        // Filter out any fields with the ORMIgnore annotation
        fields = Stream.of(fields)
        .filter(field -> !field.isAnnotationPresent(ORMIgnore.class))
        .collect(Collectors.toList()).stream().toArray(Field[]::new);
        return fields;
    }
    
    /**
     * 
     * @param obj: custome object passed by caller
     * @param n: username to search for
     * @param tableName: table to search in
     * @return sql query 
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @author razaghulam
     */
    // Expected sample output: returnQuery = "select * from Customer where username = ?"; 
    public static String findObjectByName(Object obj, String tableName) throws NoSuchFieldException, SecurityException {
    	Class<?> objClass = obj.getClass(); 
    	Field[] fields = getFields(objClass); 
    	String username = null; 
    	String value = null; 
    	for (Field field: fields) {
    		if (field.isAnnotationPresent(Username.class)) {
    			field.setAccessible(true);
    			username = field.getName(); 
    			try {
					value = field.get(obj).toString();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    			break; 
    		}
    	}
    	if (username == null) {
    		return null; 
    	}
    	 return "select * from " + tableName + " where " + username + " ='" + value + "'"; 
    	 
    }
   
    /** Gets the object's id field and generates a query
     * @author Colby Tang
    */
    public static String findObjectById(Object obj, String tableName) {
        Class<?> objClass = obj.getClass();
        Field[] fields = getFields(objClass);
        String idField = null;
        String idValue = null; 
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
            	field.setAccessible(true);
                idField = field.getName();
                try {
					idValue = field.get(obj).toString();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;
                
            }
        }
        if (idField == null) {
        	return null; 
        }

         // Create the SELECT fields
        return "SELECT * FROM " + tableName + " WHERE " + idField + "=" + idValue;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
}
