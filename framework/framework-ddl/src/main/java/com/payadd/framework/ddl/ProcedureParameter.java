package com.payadd.framework.ddl;

import java.util.ArrayList;
import java.util.List;

public class ProcedureParameter {
	public static final int IN_MODE=0;
	public static final int OUT_MODE=1;
	public static final int IN_OUT_MODE=2;
	
	private List<ParameterConfig> list ;
	
	public ProcedureParameter(){
		list = new ArrayList<ParameterConfig>();
	}

	public void add(Object value,int mode){
		list.add(new ParameterConfig(value,mode));
	}
	public void add(Object value,Class type,int mode){
		list.add(new ParameterConfig(value,type,mode));
	}
	public void addInParam(Object value){
		list.add(new ParameterConfig(value,IN_MODE));
	}
	public void addOutParam(Object value,Class type){
		list.add(new ParameterConfig(value,type,OUT_MODE));
	}
	public void addInOutParam(Object value,Class type){
		list.add(new ParameterConfig(value,type,IN_OUT_MODE));
	}
	public int size(){
		return list.size();
	}
	public ParameterConfig get(int i){
		return list.get(i);
	}
	public Object getValue(int i){
		return list.get(i).getValue();
	}
	public void setValue(int i,Object value){
		ParameterConfig pc = list.get(i);
		pc.setValue(value);
	}
}

