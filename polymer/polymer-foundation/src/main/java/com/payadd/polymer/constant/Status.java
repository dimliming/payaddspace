package com.payadd.polymer.constant;

public enum Status {
	INIT(0),UNKNOW(1),PROCESSING(2),SUCCESS(3),FAILURE(-1);
	
	private int value;
	
	private Status(int value){
		this.value = value;
	}
	public int getValue(){
		return this.value;
	}
}
