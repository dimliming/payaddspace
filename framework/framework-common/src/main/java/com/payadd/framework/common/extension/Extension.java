package com.payadd.framework.common.extension;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Extension<T> {
	private Class<T> type;
	private T instance;
	private String code;
	private String label;
	private ConcurrentMap<String, ExtensionPurpose> extensionPurposes = new ConcurrentHashMap<String, ExtensionPurpose>();
	
	public Extension(Class<T> type) {
		this.type = type;
		this.instance = instance;
	}
	
	public void addPurpose(String purpose,Method method){
		ExtensionPurpose extPurpose = new ExtensionPurpose(type, instance, purpose, method);
		this.extensionPurposes.put(purpose, extPurpose);
	}
	
	public Iterator<String> allPurpose(){
		return extensionPurposes.keySet().iterator();
	}
	public boolean isSupportedPurpose(String purpose){
		return extensionPurposes.containsKey(purpose);
	}
	
	public ExtensionPurpose getPurpose(String purpose){
		ExtensionPurpose extPurpose = extensionPurposes.get(purpose);
		if (extPurpose==null){
			throw new RuntimeException("Class can't support purpose \""+purpose+"\"");
		}
		return extPurpose;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public T getInstance() {
		return instance;
	}

	public void setInstance(T instance) {
		this.instance = instance;
	}

	public Class<T> getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
