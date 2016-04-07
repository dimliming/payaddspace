package com.payadd.polymer.model.common;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class RawMessage {
	private Map<String,String> fieldMap = new TreeMap<String, String>();
	
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

	public Map<String, String> getFieldMap() {
		return fieldMap;
	}

	
}
