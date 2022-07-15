package com.revature.razangorm.orm;

import java.util.List;

public interface ObjectRelationalMapper {
    public Object create(Object obj, String s);
	public List<Object> findAll(Object obj, String s);
    public Object update(Object obj);
	public Object delete(Object obj); 
}
