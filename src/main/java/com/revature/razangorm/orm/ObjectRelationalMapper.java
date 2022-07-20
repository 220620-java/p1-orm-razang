package com.revature.razangorm.orm;

import java.util.List;

public interface ObjectRelationalMapper {
    public Object create(Object obj, String c);
	public List<Object> findAll(Class<? extends Object> cl, String c);
    public Object update(Object obj, String c);
	public Object delete(Object obj, String c); 
	public Object findByName(Object obj, String c); 
	public Object findById(Object obj, String c); 
}
