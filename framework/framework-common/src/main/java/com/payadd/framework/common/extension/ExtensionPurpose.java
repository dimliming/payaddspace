package com.payadd.framework.common.extension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExtensionPurpose {
	private final Class type;
	private final Object instance;
	private final String purpose;
	private final Method method;
	
	public ExtensionPurpose(Class type,Object instance,String purpose,Method method){
		this.type = type;
		this.instance = instance;
		this.purpose = purpose;
		this.method = method;
	}
	
	public Object execute(Object[] parameters) throws IllegalAccessException,IllegalArgumentException,InvocationTargetException {
		return this.method.invoke(instance, parameters);
	}

	public Class getType() {
		return type;
	}

	public Object getInstance() {
		return instance;
	}

	public String getPurpose() {
		return purpose;
	}

	public Method getMethod() {
		return method;
	}
	
	
}
