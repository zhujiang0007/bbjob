/*
 * File Name：ReflectHelper.java
 * Comments: 反射操作工具类
 * Author: 
 * Create Date: 2013-4-8 上午11:13:17
 * Modified By: 
 * Modified Date: 
 * Why & What is modified: 
 * version: V1.0 
 */
package com.rundatop.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 *	反射操作工具类
 */
public class ReflectHelper {
	/**
	 * 获取obj对象fieldName的Field
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if(field!=null){
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * 设置obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}

	/** Default constructor */
	public ReflectHelper() {
	}
	
	/** 
     * java反射bean的set方法 
     *  
     * @param objectClass 
     * @param fieldName 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Method getSetMethod(Class objectClass, String fieldName) {  
        try {  
            Class[] parameterTypes = new Class[1];  
            Field field = objectClass.getDeclaredField(fieldName);  
            parameterTypes[0] = field.getType();  
            StringBuffer sb = new StringBuffer();  
            sb.append("set");  
            sb.append(fieldName.substring(0, 1).toUpperCase());  
            sb.append(fieldName.substring(1));  
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);  
            return method;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    /** 
     * java反射bean的get方法 
     *  
     * @param objectClass 
     * @param fieldName 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Method getGetMethod(Class objectClass, String fieldName) {  
        StringBuffer sb = new StringBuffer();  
        sb.append("get");  
        sb.append(fieldName.substring(0, 1).toUpperCase());  
        sb.append(fieldName.substring(1));  
        try {  
            return objectClass.getMethod(sb.toString());  
        } catch (Exception e) {  
        }  
        return null;  
    }
    
    /** 
     * 执行set方法 
     *  
     * @param o 执行对象 
     * @param fieldName 属性 
     * @param value 值 
     */  
    public static void invokeSet(Object o, String fieldName, Object obj) {  
        Method method = getSetMethod(o.getClass(), fieldName);  
        try { 
        	Class<?>[] clazz = method.getParameterTypes();  
            String type = clazz[0].getName();  
            if(type.equals("java.lang.String")){  
                method.invoke(o,(String)obj);  
            }  
            else if(type.equals("java.util.Date")){ 
            	SimpleDateFormat df = null;
            	if(obj.toString().indexOf(":")!=-1){
            		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			}else{
    				df = new SimpleDateFormat("yyyy-MM-dd");
    			}
            	method.invoke(o, df.parse(String.valueOf(obj)));
            }  
            else if(type.equals("java.lang.Integer")||type.equals("int")){  
                method.invoke(o, new Integer((String)obj));  
            }
            else if(type.equals("java.lang.Long")||type.equals("long")){
            	method.invoke(o, new Long((String)obj));
            }
            else if(type.equals("java.math.BigDecimal")){
            	method.invoke(o, new BigDecimal((String)obj));
            }
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 执行get方法  
     * @param o 执行对象 
     * @param fieldName 属性 
     */  
    public static Object invokeGet(Object o, String fieldName) {  
        Method method = getGetMethod(o.getClass(), fieldName);  
        try {       	
            return method.invoke(o, new Object[0]);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
    
}
