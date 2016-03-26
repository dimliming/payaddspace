package com.payadd.polymer.model.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RawMessage {
	private Map<String,String> fieldMap = new HashMap<String, String>();
	
	public RawMessage() {
	}
	
	public void addField(String name,String value){
		fieldMap.put(name, value);
	}
	public String getField(String name){
		return fieldMap.get(name);
	}
	
	public Iterator<String> allField(){
		return  fieldMap.keySet().iterator();
	}
}
