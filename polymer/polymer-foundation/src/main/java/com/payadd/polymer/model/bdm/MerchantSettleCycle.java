package com.payadd.polymer.model.bdm;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 商户结算周期
 * @project PayAdd
 * @Date 2016-3-29
 * @author ming.li
 *
 */
public class MerchantSettleCycle {
	// 商户编码
	private String merchantCode;
	// 结算方式
	private char settleRule;//1-T+N，2-定时，3-定额，4-手工
	// 资金沉淀天数
	private int retainDays;
	// 定额额度
	private BigDecimal minAmt;
	// 定时周期长度
	private char regularType;//W：周 M:月
	// 定时周期时间
	private int regularTime;//第几日
	// 遇节假日调整标志
	private char holidayFlag;//0:不调整结费执行时间、1:提前执行(在节假日前最后一个工作天)、2:延期执行(在节假日后第一个工作天)
	// 下一结算时间
	private int nextSettletime;
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

	public char getSettleRule() {
		return settleRule;
	}

	public void setSettleRule(char settleRule) {
		this.settleRule = settleRule;
	}

	public int getRetainDays() {
		return retainDays;
	}

	public void setRetainDays(int retainDays) {
		this.retainDays = retainDays;
	}

	public BigDecimal getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(BigDecimal minAmt) {
		this.minAmt = minAmt;
	}

	public char getRegularType() {
		return regularType;
	}

	public void setRegularType(char regularType) {
		this.regularType = regularType;
	}

	public int getRegularTime() {
		return regularTime;
	}

	public void setRegularTime(int regularTime) {
		this.regularTime = regularTime;
	}

	public char getHolidayFlag() {
		return holidayFlag;
	}

	public void setHolidayFlag(char holidayFlag) {
		this.holidayFlag = holidayFlag;
	}

	public int getNextSettletime() {
		return nextSettletime;
	}

	public void setNextSettletime(int nextSettletime) {
		this.nextSettletime = nextSettletime;
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
