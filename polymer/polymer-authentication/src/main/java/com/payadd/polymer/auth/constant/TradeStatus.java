package com.payadd.polymer.auth.constant;

public class TradeStatus {
	// 0-未提交渠道,1-交易终止,2-已提交渠道,3-认证失败,4-认证成功
	public static final int CHANNAL_UNCOMMIT = 0;
	public static final int CHANNAL_COMMIT = 2;
	public static final int AUTH_FAIL = 3;
	public static final int AUTH_SUCCESS = 4;
	public static final int AUTH_STOP = 1;
}
