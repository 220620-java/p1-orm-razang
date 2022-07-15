package com.revature.razangorm.orm;

import com.revature.razangorm.orm.*;

public interface ObjectRelationalMapper {
    public Object create(Object obj);
	public Object findAll();
    public Object update(Object obj);
	public Object delete(Object obj); 
}
