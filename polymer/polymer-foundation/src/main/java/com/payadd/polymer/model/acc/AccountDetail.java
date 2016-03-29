package com.payadd.polymer.model.acc;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 账户收支明细
 * @Project PayAdd
 * @Date 2016-3-29
 * @author ming.li
 *
 */
public class AccountDetail {
	//主键
	private long id;
	//账户编号
	private String accountNo;
	//商户编号
	private String merchantCode;
	//交易时间
	private Timestamp tradeTime;
	//记账时间
	private Timestamp accountTime;
	//交易类型
	private int tradeType;//10-充值,20-认证
	//交易编号
	private String tradeNo;
	//金额
	private BigDecimal amt;
	//变动后余额
	private BigDecimal balance;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public Timestamp getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Timestamp tradeTime) {
		this.tradeTime = tradeTime;
	}
	public Timestamp getAccountTime() {
		return accountTime;
	}
	public void setAccountTime(Timestamp accountTime) {
		this.accountTime = accountTime;
	}
	public int getTradeType() {
		return tradeType;
	}
	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
