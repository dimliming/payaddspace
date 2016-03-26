package com.payadd.framework.common.exception;

public class SystemException extends RuntimeException {
	private static final long serialVersionUID = 3546991612477091597L;
	private String errorCode;
	private String errorMsg;
	
	public SystemException(String message){
		super(message);
	}
	public SystemException(String message,Exception e){
		super(message,e);
	}
	public SystemException(Exception e){
		super(e);
	}
	public SystemException(String errorCode, String errorMsg) {
		super(errorCode + ":" + errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
