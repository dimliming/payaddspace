package com.payadd.polymer.model.bdm;

import java.sql.Timestamp;

/**
 * 商户绑定银行卡信息
 * @Project PayAdd
 * @Date 2016-3-29
 * @author ming.li
 *
 */
public class MerchantBankAccount {
	// 商户编码
	private String merchantCode;
	// 结算账户
	private String accountNo;
	// 户名
	private String accountName;
	// 开户行代码
	private String bankCode;
	// 开户行名称
	private String bankName;
	// 开户行地址
	private String bankAddress;
	// 清算行行号
	private String settleCode;
	// 对公对私标志
	private char pcFlag;// C-对公，P-对私
	// 是否默认账户
	private char isDefault;// N-否，Y-是
	// 用途
	private String usage;
	// 建立用户ID
	private String createUserId;
	// 最后更新用户ID
	private String updateUserId;
	// 建立时间
	private Timestamp createTime;
	// 最后更新时间
	private Timestamp lastUpdateTime;

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getSettleCode() {
		return settleCode;
	}

	public void setSettleCode(String settleCode) {
		this.settleCode = settleCode;
	}

	public char getPcFlag() {
		return pcFlag;
	}

	public void setPcFlag(char pcFlag) {
		this.pcFlag = pcFlag;
	}

	public char getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(char isDefault) {
		this.isDefault = isDefault;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
