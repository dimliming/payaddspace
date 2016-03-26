package com.payadd.polymer.model.common;

public class TransactionIdentity {
	private String merchantTradeCode;
	private String productTradeCode;
	private String channelTradeCode;
	public String getMerchantTradeCode() {
		return merchantTradeCode;
	}
	public void setMerchantTradeCode(String merchantTradeCode) {
		this.merchantTradeCode = merchantTradeCode;
	}
	public String getProductTradeCode() {
		return productTradeCode;
	}
	public void setProductTradeCode(String productTradeCode) {
		this.productTradeCode = productTradeCode;
	}
	public String getChannelTradeCode() {
		return channelTradeCode;
	}
	public void setChannelTradeCode(String channelTradeCode) {
		this.channelTradeCode = channelTradeCode;
	}
	
	
}
