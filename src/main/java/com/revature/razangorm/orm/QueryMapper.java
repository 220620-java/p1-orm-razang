package com.revature.razangorm.orm;

// java imports
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.revature.razangorm.annotations.Id;
import com.revature.razangorm.annotations.ORMIgnore;
import com.revature.razangorm.annotations.Subclass;
import com.revature.razangorm.annotations.Username; 

/**
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
        String valuesQuery = " VALUES (";
        StringJoiner insertJoiner = new StringJoiner(",");
        StringJoiner valuesJoiner = new StringJoiner(",");
        Stream<Field> fieldsStream = Arrays.stream(fields);
        fieldsStream.forEach(
            field -> {
                // Add each field separated by a comma
                insertJoiner.add(field.getName());

                // Add ? for each field in fields
                valuesJoiner.add("?");
            }
        );
        insertQuery += insertJoiner.toString();
        insertQuery += ")";
        
        valuesQuery += valuesJoiner.toString() + ");";
        return insertQuery += valuesQuery;
    }

    /** Gets the object's id field and generates a query
     * @author Colby Tang
    */
    public static String getObjectById (Object obj, String tableName) {
        Class<?> objClass = obj.getClass();
        Field[] fields = getFields(objClass);
        String idField = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field.getName();
                break;
            }
        }

         // Create the SELECT fields
        return "SELECT * FROM " + tableName + " WHERE " + idField + "=?";
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
        .collect(Collectors.toList())
        .toArray(Field[]::new);
        return fields;
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
     */
    // Expected sample output: returnQuery = "update Customer set email=? where customer_id=?";
<<<<<<< HEAD
    
    public static String updateObject(Object obj, String[] updateFields, String tableName) throws NoSuchFieldException, SecurityException {
=======
    public static String updateObjectByEmail(Object obj, String eMail, String tableName) throws NoSuchFieldException, SecurityException {
>>>>>>> f11c931e252a93bacdb84f6d44efbfd4cbb7ac2a
    	Class<?> objClass = obj.getClass();
    	
    	String c_id = objClass.getDeclaredField("customer_id").getName();
    	String  email = objClass.getDeclaredField("email").getName(); 
    	String returnQuery = "update " + tableName + " set "; 
    	for(String field: updateFields) {
    		returnQuery += field + " =?,"; 
    	}
    	returnQuery = returnQuery.replace(", 4", " "); 
    	returnQuery += " where " + c_id + " =?";  
    	return returnQuery;
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
    public static String findObjectByName(Object obj, String n, String tableName) throws NoSuchFieldException, SecurityException {
    	Class<?> objClass = obj.getClass(); 
    	Field[] fields = getFields(objClass); 
    	String username = ""; 
    	for (Field field: fields) {
    		if (field.isAnnotationPresent(Username.class)) {
    			username = field.getName(); 
    			break; 
    		}
    	}
    	 return "select * from " + tableName + " where " + username + " =?"; 
    	 
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
    
    // Expected sample output: returnQuery  "delete from Customer where  customer_id = ?";
    public static String deleteObject(Object obj, String tableName) throws NoSuchFieldException, SecurityException {
    	Class<?> objClass = obj.getClass();
    	String customer_id = objClass.getDeclaredField("customer_id").getName();
    	String returnQuery = "delete from " + tableName + " where " + customer_id + " =?"; 
    	
		return returnQuery;
    }
    
    /**
     * 
     * @param tableName
     * @return
     */
    public static String readObjects(String tableName) {
    	
    	String returnQuery = "select * from " + tableName; 
		return returnQuery;
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
