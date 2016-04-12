package com.payadd.polymer.auth.constant;

public class MessageFields {
	
	 public static final String VERSION = "version";
	 public static final String SIGNATURE = "signature";
	 public static final String ENCODING = "encoding";
	 public static final String TXN_TYPE = "txn_type";
	 public static final String MERCHANT_CODE = "merchant_code";
	 public static final String ORDER_NO = "order_no";
	 public static final String TRADE_TIME = "trade_time";
	 public static final String AUTH_TYPE = "auth_type";
	 public static final String ACCOUNT_TYPE = "account_type";
	 public static final String ACCOUNT_NO = "account_no";
	 public static final String CERT_TYPE = "cert_type";
	 public static final String CERT_NO = "cert_no";
	 public static final String CUSTOM_NAME = "custom_name";
	 public static final String PHONE = "phone";

	 public final static String[] AUTH_FIELDS = { VERSION, SIGNATURE, ENCODING, TXN_TYPE, MERCHANT_CODE,
				ORDER_NO, TRADE_TIME, AUTH_TYPE, ACCOUNT_TYPE, ACCOUNT_NO};
		 public final static String[] ENQUIRY_FIELDS = { VERSION, SIGNATURE, ENCODING, TXN_TYPE, MERCHANT_CODE,
					ORDER_NO};
}
