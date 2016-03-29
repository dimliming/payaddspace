package com.payadd.polymer.model.aut;

/**
 * 渠道请求日志
 * @Project PayAdd
 * @Date 2016-3-29
 * @author ming.li
 *
 */
public class ChannelMessage {
	//主键
	private long id;
	//交易号
	private String tradeNo;
	//渠道代号
	private String channelCode;
	//商户编码
	private String merchantCode;
	//报文类型
	private int msgType;//1-认证,2-查询,3-反馈
	//接收报文
	private String reqMsg;
	//反馈报文
	private String respMsg;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getReqMsg() {
		return reqMsg;
	}
	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
}
