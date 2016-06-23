package com.payadd.polymer.model.com;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 产品
 * 
 * @author ddm
 * @since 2016-06-23
 */
public class Product {
	private String productCode;
	private String productName;
	private String accType;
	private BigDecimal commonFee;
	private Timestamp createTime;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getCommonFee() {
		return commonFee;
	}

	public void setCommonFee(BigDecimal commonFee) {
		this.commonFee = commonFee;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
