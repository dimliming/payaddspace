package com.payadd.polymer.model.aut;

public class AuthDockingConfig {
	private String authURL;
	private String enquiryURL;
	private String md5Key;
	private String subMerchantCode;
	
	public String getAuthURL() {
		return authURL;
	}
	public void setAuthURL(String authURL) {
		this.authURL = authURL;
	}
	public String getEnquiryURL() {
		return enquiryURL;
	}
	public void setEnquiryURL(String enquiryURL) {
		this.enquiryURL = enquiryURL;
	}
	public String getMd5Key() {
		return md5Key;
	}
	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}
	public String getSubMerchantCode() {
		return subMerchantCode;
	}
	public void setSubMerchantCode(String subMerchantCode) {
		this.subMerchantCode = subMerchantCode;
	}
	
	
}
