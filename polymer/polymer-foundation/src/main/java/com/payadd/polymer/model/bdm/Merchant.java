package com.payadd.polymer.model.bdm;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 商户信息
 * 
 * @Project PayAdd
 * @Date 2016-3-29
 * @author ming.li
 *
 */
public class Merchant {
	// 商户编码
	private String merchantCode;
	// 商户全称
	private String fullName;
	// 商户简称
	private String shortName;
	// 商户状态
	private String status; // 1-正常，2-禁止，3-销户
	// 商户名单标志
	private String listFlag;// 1-正常，2-黑名单，3-灰名单，4-红名单
	// 商户阶层
	private String level;// 维护商户等级之用。先默认为‘00’，预留
	// 网址名称
	private String siteName;
	// 网址地址
	private String siteAddress;
	// 营业执照
	private String businessLicense;
	// 组织机构代码
	private String orgCode;
	// 证件有效开始时间
	private Integer idDurationStart;
	// 证件有效结束时间
	private Integer idDurationEnd;
	// 法定代表人姓名
	private String representName;
	// 法定代表人证件号码
	private String representIdCode;
	// 经营范围
	private String businessScope;
	// 注册资金
	private BigDecimal registeredCapital;
	// 注册地址
	private String registeredAddress;
	// 行业分类
	private String industryClass;
	// EMAIL地址
	private String companyEmail;
	// 单位联系地址
	private String companyAddress;
	// 单位联系电话
	private String companyPhone;
	// 单位联系传真号码
	private String companyTax;
	// 联系人姓名
	private String contactName;
	// 联系人职务
	private String contactPosition;
	// 联系人性别
	private String contactSex; // F-女，M-男
	// 联系人邮箱
	private String contactEmail;
	// 联系人手机
	private String contactPhone;
	// 联系人固话
	private String contactTel;
	// 联系人QQ
	private String contactQQ;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getIdDurationStart() {
		return idDurationStart;
	}

	public void setIdDurationStart(Integer idDurationStart) {
		this.idDurationStart = idDurationStart;
	}

	public Integer getIdDurationEnd() {
		return idDurationEnd;
	}

	public void setIdDurationEnd(Integer idDurationEnd) {
		this.idDurationEnd = idDurationEnd;
	}

	public String getRepresentName() {
		return representName;
	}

	public void setRepresentName(String representName) {
		this.representName = representName;
	}

	public String getRepresentIdCode() {
		return representIdCode;
	}

	public void setRepresentIdCode(String representIdCode) {
		this.representIdCode = representIdCode;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public BigDecimal getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(BigDecimal registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getIndustryClass() {
		return industryClass;
	}

	public void setIndustryClass(String industryClass) {
		this.industryClass = industryClass;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyTax() {
		return companyTax;
	}

	public void setCompanyTax(String companyTax) {
		this.companyTax = companyTax;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPosition() {
		return contactPosition;
	}

	public void setContactPosition(String contactPosition) {
		this.contactPosition = contactPosition;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactQQ() {
		return contactQQ;
	}

	public void setContactQQ(String contactQQ) {
		this.contactQQ = contactQQ;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getListFlag() {
		return listFlag;
	}

	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}

	public String getContactSex() {
		return contactSex;
	}

	public void setContactSex(String contactSex) {
		this.contactSex = contactSex;
	}
	

}
