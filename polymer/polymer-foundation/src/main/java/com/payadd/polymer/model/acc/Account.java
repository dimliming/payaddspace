package com.payadd.polymer.model.acc;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 账户
 * @Project PayAdd
 * @Date 2016-3-29
 * @author ming.li
 *
 */
public class Account {
	//账户编号
	private String accountNo;
	//商户编号
	private String merchantCode;
	//开户日期
	private Timestamp openTime;
	//账户余额
	private BigDecimal balance;
	//账户状态
	private int status; //0-正常,1-销户
	//时间戳
	private long timestamp;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public Timestamp getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Timestamp openTime) {
		this.openTime = openTime;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
