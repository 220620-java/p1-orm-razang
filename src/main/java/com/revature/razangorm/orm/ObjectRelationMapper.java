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

/**
 * @author Team Razang
 */
public class ObjectRelationMapper {

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

    /** WORK IN PROGRESS
     * @author Colby Tang
    */
    public static String getObjectById (Object obj, String tableName) {
        Class<?> objClass = obj.getClass();
        Field[] fields = getFields(objClass);
        Field idField = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
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

    
    // String sql = "update Customer "
	//+ "set email=? " 
	//+ "where customer_id=?";
    public static String updateObjectByEmail(Object obj, String eMail, String tableName) throws NoSuchFieldException, SecurityException {
    	Class<?> objClass = obj.getClass();
    	String c_id = objClass.getDeclaredField("customer_id").getName();
    	String  email = objClass.getDeclaredField("email").getName(); 
    	String returnQuery = "update " + tableName + " set " + email + "=? " + "where " + c_id + " =?";  
    	return returnQuery;
    }
}
