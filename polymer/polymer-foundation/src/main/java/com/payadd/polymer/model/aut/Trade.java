package com.payadd.polymer.model.aut;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 认证交易表
 * @Project PayAdd
 * @Date 2016-3-29
 * @author ming.li
 *
 */
public class Trade {
	//交易号
	private String tradeNo;
	//商户编码
	private String merchantCode;
	//商户名称
	private String fullName;
	//商户交易号
	private String merchantTradeNO;
	//渠道交易号
	private String channelTradeNo;//视渠道返回情况，可为空
	//渠道代号
	private String channelCode;
	//交易时间
	private Timestamp tradeTime;
	//验证方式
	private String authType;//1000：卡号验证,0100：身份证号验证,0010：姓名验证,0001：手机号验证
	//账号
	private String accountNo;
	//证件类型 
	private String certType;//01：身份证,02：军官证,03：护照,04：回乡证,05：台胞证,06：警官证,07：士兵证,99：其它证件
	//证件号码
	private String certNo;
	//姓名
	private String customName;
	//手机号
	private String phone;
	//交易费用
	private BigDecimal fee;
	 //是否已核销
	private char isWriteOff;//Y-是,N-否
	//渠道应答码
	private String channelRespCode;
	//渠道应答消息
	private String channelRespMsg;
	//状态
	private int status;//0-未提交渠道,1-已提交渠道,2-认证失败,3-认证成功
	//商户通知URL
	private String notifyUrl;//对于异步接口，需要传递这个字段
	//反馈状态
	private int notifyStatus;//0-未反馈,1-已反馈
	//反馈次数
	private int notifyNum;
	//反馈时间
	private Timestamp notifyTime;
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getMerchantTradeNO() {
		return merchantTradeNO;
	}
	public void setMerchantTradeNO(String merchantTradeNO) {
		this.merchantTradeNO = merchantTradeNO;
	}
	public String getChannelTradeNo() {
		return channelTradeNo;
	}
	public void setChannelTradeNo(String channelTradeNo) {
		this.channelTradeNo = channelTradeNo;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Timestamp getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Timestamp tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public char getIsWriteOff() {
		return isWriteOff;
	}
	public void setIsWriteOff(char isWriteOff) {
		this.isWriteOff = isWriteOff;
	}
	public String getChannelRespCode() {
		return channelRespCode;
	}
	public void setChannelRespCode(String channelRespCode) {
		this.channelRespCode = channelRespCode;
	}
	public String getChannelRespMsg() {
		return channelRespMsg;
	}
	public void setChannelRespMsg(String channelRespMsg) {
		this.channelRespMsg = channelRespMsg;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public int getNotifyStatus() {
		return notifyStatus;
	}
	public void setNotifyStatus(int notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
	public int getNotifyNum() {
		return notifyNum;
	}
	public void setNotifyNum(int notifyNum) {
		this.notifyNum = notifyNum;
	}
	public Timestamp getNotifyTime() {
		return notifyTime;
	}
	public void setNotifyTime(Timestamp notifyTime) {
		this.notifyTime = notifyTime;
	}
	
}
