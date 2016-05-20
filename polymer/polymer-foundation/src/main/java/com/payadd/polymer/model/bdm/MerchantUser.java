package com.payadd.polymer.model.bdm;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Project PayAdd
 * @Date 2016-3-29
 * @author ming.li
 *
 */
public class MerchantUser implements Serializable {
	// 主键
	private long id;
	// 商户编码
	private String merchantCode;
	// 用户名
	private String userName;
	// 手机号
	private String phone;
	// 邮箱
	private String email;
	// 登陆名
	private String loginName;
	// 登陆密码
	private String password;
	// 支付密码
	private String payPassword;
	// 欢迎信息
	private String welcome;
	// 安全问题
	private String secureQuestion;
	// 安全回答
	private String secureAnswer;
	// 经办权限
	private String authAgency;
	// 复核权限
	private String authCheck;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	public String getSecureQuestion() {
		return secureQuestion;
	}

	public void setSecureQuestion(String secureQuestion) {
		this.secureQuestion = secureQuestion;
	}

	public String getSecureAnswer() {
		return secureAnswer;
	}

	public void setSecureAnswer(String secureAnswer) {
		this.secureAnswer = secureAnswer;
	}

	public String getAuthAgency() {
		return authAgency;
	}

	public void setAuthAgency(String authAgency) {
		this.authAgency = authAgency;
	}

	public String getAuthCheck() {
		return authCheck;
	}

	public void setAuthCheck(String authCheck) {
		this.authCheck = authCheck;
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
