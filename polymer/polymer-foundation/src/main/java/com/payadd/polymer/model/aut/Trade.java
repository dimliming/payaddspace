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
	private String merchantTradeNo;
	//渠道交易号
	private String channelTradeNo;//视渠道返回情况，可为空
	
	//产品编号
	private String productCode;
	//机构代码
	private String agencyCode;
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
	private String isWriteOff;//Y-是,N-否
	//渠道应答码
	private String respCode;
	//渠道应答消息
	private String respMsg;
	//状态
	private Integer status;//0-未提交渠道,1-已提交渠道,2-认证失败,3-认证成功
	//商户通知URL
	private String notifyUrl;//对于异步接口，需要传递这个字段
	//反馈状态
	private Integer notifyStatus;//0-未反馈,1-已反馈
	//反馈次数
	private Integer notifyNum;
	//反馈时间
	private Timestamp notifyTime;
	//是否测试
	private String isTest;
	
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

	public String getMerchantTradeNo() {
		return merchantTradeNo;
	}
	public void setMerchantTradeNo(String merchantTradeNo) {
		this.merchantTradeNo = merchantTradeNo;
	}
	public String getChannelTradeNo() {
		return channelTradeNo;
	}
	public void setChannelTradeNo(String channelTradeNo) {
		this.channelTradeNo = channelTradeNo;
	}


	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
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


	public String getIsWriteOff() {
		return isWriteOff;
	}
	public void setIsWriteOff(String isWriteOff) {
		this.isWriteOff = isWriteOff;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getNotifyStatus() {
		return notifyStatus;
	}
	public void setNotifyStatus(Integer notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
	public Integer getNotifyNum() {
		return notifyNum;
	}
	public void setNotifyNum(Integer notifyNum) {
		this.notifyNum = notifyNum;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Timestamp getNotifyTime() {
		return notifyTime;
	}
	public void setNotifyTime(Timestamp notifyTime) {
		this.notifyTime = notifyTime;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getIsTest() {
		return isTest;
	}
	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}
	
}
