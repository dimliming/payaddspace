package com.payadd.polymer.model.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.payadd.polymer.constant.CapsulationType;
import com.payadd.polymer.constant.Status;

public class Result {
	private Status status;
	private String resultCode;
	private String resultDesc;
	private CapsulationType capType;
	private Object bean;
	private String url;
	private Map<String,Object> detail = new HashMap<String,Object>();
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}
	
	public void addDetail(String name,Object value){
		this.addDetail(name, value);
	}
	public Iterator<String> allDetailsKey(){
		return this.detail.keySet().iterator();
	}
	public CapsulationType getCapType() {
		return capType;
	}
	public void setCapType(CapsulationType capType) {
		this.capType = capType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
