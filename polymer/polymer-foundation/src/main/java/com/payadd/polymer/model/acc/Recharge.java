package com.payadd.polymer.model.acc;

import java.sql.Timestamp;

/**
 * 账户充值记录
 * 
 * @Project PayAdd
 * @Date 2016-3-29
 * @author ming.li
 *
 */
public class Recharge {
	// 充值编号
	private String rechargeNo;
	// 商户编号
	private String merchantCode;
	// 充值时间
	private Timestamp rechargeTime;
	// 充值渠道
	private String channelCode;
	// 金额
	private Timestamp amt;
	
	public String getRechargeNo() {
		return rechargeNo;
	}

	public void setRechargeNo(String rechargeNo) {
		this.rechargeNo = rechargeNo;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Timestamp getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Timestamp rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Timestamp getAmt() {
		return amt;
	}

	public void setAmt(Timestamp amt) {
		this.amt = amt;
	}

}
