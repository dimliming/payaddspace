package com.payadd.framework.ddl;

public class ParameterConfig{
	private Object value;
	private int mode;
	private Class type;
	
	public ParameterConfig(Object value,Class type,int mode){
		this.value = value;
		this.mode = mode;
		this.type = type;
	}
	public ParameterConfig(Object value,int mode){
		this.value = value;
		this.mode = mode;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	
}
