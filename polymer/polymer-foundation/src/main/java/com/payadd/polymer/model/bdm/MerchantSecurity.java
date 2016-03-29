package com.payadd.polymer.model.bdm;

/**
 * 商户安全配置
 * 
 * @Project PayAdd
 * @Date 2016-3-29
 * @author ming.li
 */
public class MerchantSecurity {
	// 主键
	private long id;
	// 商户编码
	private String merchantCode;
	// 协议编号
	private String protocolCode;
	// 签名算法
	private char signAlg; //M-MD5,S-SHA1,R-RSA
	// 签名Key
	private String signKey;
	// 加密算法
	private String cryptoAlg;//AES,DES,3DES
	// 加密Key
	private String cryptoKey;
	// RSA公钥
	private String publicKey;
	// RSA私钥
	private String privateKey;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public char getSignAlg() {
		return signAlg;
	}

	public void setSignAlg(char signAlg) {
		this.signAlg = signAlg;
	}

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public String getCryptoAlg() {
		return cryptoAlg;
	}

	public void setCryptoAlg(String cryptoAlg) {
		this.cryptoAlg = cryptoAlg;
	}

	public String getCryptoKey() {
		return cryptoKey;
	}

	public void setCryptoKey(String cryptoKey) {
		this.cryptoKey = cryptoKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
}
