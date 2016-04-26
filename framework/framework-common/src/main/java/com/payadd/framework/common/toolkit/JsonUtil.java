package com.payadd.framework.common.toolkit;

import java.lang.reflect.Field;
import java.util.List;

public class JsonUtil {
	public static String toJson(Object obj){
		if (obj==null)return "{}";
		
		Field[] fields = obj.getClass().getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		boolean hasNotNullField = false;
		sb.append("{");
		for (int i=0;i<fields.length;i++){
			Field field = fields[i];
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value = null;
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			//System.out.println("field:"+fieldName+",value:"+value);
			if (value!=null){
				if (hasNotNullField){
					sb.append(",");
				}
				sb.append("\"").append(fieldName).append("\":").append("\"").append(value).append("\"");
				
				hasNotNullField = true;
			}
		}
		sb.append("}");
		
		return sb.toString();
	}
	public static String toJson(List<Object> list){
		if (list==null||list.size()==0){
			return "[]";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i=0;i<list.size();i++){
			if (i>0){
				sb.append(",");
			}
			sb.append(toJson(list.get(i)));
		}
		sb.append("]");
		
		return sb.toString();
	}
}
