package com.payadd.polymer.model.common;

import com.payadd.polymer.model.bdm.Merchant;

public class ProductContext<T> {
	private String protocolCode;
	private String productCode;
	
	private String merchantTradeNo;
	private String productTradeNo;
	
	private Merchant merchant;
	private T productTrade;
	public String getProtocolCode() {
		return protocolCode;
	}
	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getMerchantTradeNo() {
		return merchantTradeNo;
	}
	public void setMerchantTradeNo(String merchantTradeNo) {
		this.merchantTradeNo = merchantTradeNo;
	}
	public String getProductTradeNo() {
		return productTradeNo;
	}
	public void setProductTradeNo(String productTradeNo) {
		this.productTradeNo = productTradeNo;
	}
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	public T getProductTrade() {
		return productTrade;
	}
	public void setProductTrade(T productTrade) {
		this.productTrade = productTrade;
	}
	
	
	
}
