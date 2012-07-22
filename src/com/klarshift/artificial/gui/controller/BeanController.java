package com.klarshift.artificial.gui.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanController {
	Object object;
	String propertyName;
	
	Method delegateSet, delegateGet;
	
	public BeanController(Object object, String propertyName, Class type){
		this.object = object;
		this.propertyName = propertyName;
		
		// get setter method
		String name = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1, propertyName.length());
		try {
			delegateGet = object.getClass().getMethod("get"+name, null);
			delegateSet = object.getClass().getMethod("set"+name, type);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setValue(Object value){
		try {
			System.out.println("SET " + value.getClass());
			delegateSet.invoke(object, value);			
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object getValue(){
		try {
			return delegateGet.invoke(object, null);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
