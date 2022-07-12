package com.testing.orm;



import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Colby Tang
 * @author Team Synergy
 */
public class ObjectRelationMapper {
    // Call method example: String sqlQuery = ObjectRelationMapper.insertObject(student, "users");
    // Returns something like
    // INSERT INTO users (id,firstName,lastName,username,email,userType,salt,major,gpa) VALUES (?,?,?,?,?,?,?,?,?);
    /**
     * Takes an object and returns an SQL insert statement to insert into database.
     */
    public static String createObject (Object obj, String tableName) {
        Class<?> objClass = obj.getClass();
        Field[] fields = getFields(objClass);
        
        // Create the insert fields
        String returnQuery = "INSERT INTO " + tableName + " (";
        StringJoiner joiner = new StringJoiner(",");
        Stream<Field> fieldsStream = Arrays.stream(fields);
        fieldsStream.forEach(field -> joiner.add(field.getName()));
        returnQuery += joiner.toString();
        returnQuery += ")";
        
        // Add ? for each field in fields
        returnQuery += " VALUES (";
        StringJoiner joiner2 = new StringJoiner(",");
        Stream<Field> fieldsStream2 = Arrays.stream(fields);
        fieldsStream2.forEach(field -> joiner2.add("?"));
        returnQuery += joiner2.toString();
        returnQuery += ");";
        return returnQuery;
    }

    public static String getObjectById (Object obj, int id, String tableName) {
        Class<?> objClass = obj.getClass();
        Field[] fields = getFields(objClass);

        // Create the SELECT fields
        String returnQuery = "SELECT * FROM " + tableName + "WHERE ";
        returnQuery += ")";

        return returnQuery;
    }

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
}
