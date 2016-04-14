package com.payadd.polymer.demo;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;

/**
 *
 * 带卡号四要素验证 Created by qjj on 15-11-24.
 */
public class AuthDemo {
	// 每个商户分配md5盐值
	static String signKey = "fd54febfc1dc1d5e10f5780b6ae6333c";
	// 分配商户号
	static String merid = "310012301000011";

	static String pan = "6227003321740369152";
	static String certifId = "440902199307090413";
	// static String certifId="332522199602048234";
	static String username = "黎铭";
	static String phone = "13428187972";

	public static void main(String[] args) throws Exception {
		Map<String, String> contentData = new HashMap<String, String>();
		contentData.put("version", "1.0.0");
		contentData.put("encoding", "UTF-8");
		// 00 认证
		contentData.put("txn_type", "00");
		contentData.put("merId", merid);
		contentData.put("merchant_code", "310012301000011");
		contentData.put("order_no", "310012301000011");
		contentData.put("trade_time", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		// defaultPayType 位图形式
		// 第一位数字是1代表卡号，第二为数字1代表验证身份证，第三位为数字1代表验证姓名，第四位为数字１代表验证手机
		contentData.put("auth_type", "1111");
		contentData.put("account_type", "01");
		// 01 卡
		contentData.put("account_no", pan);
		// 01身份证
		contentData.put("cert_type", "01");
		contentData.put("cert_no", certifId);
		contentData.put("custom_name", username);
		contentData.put("phone", phone);
		String sign = signData(contentData);
		contentData.put("signature", sign);
		HttpClient client = new HttpClient("https://139.196.192.53:8443/authen/auth.htm", 6000, 60000);
		client.send(contentData, "utf-8");
		String respons = client.getResult();
		String str = URLDecoder.decode(respons, "utf-8");
		
		System.out.println(getMapByRespBody(str));

	}
	  public static Map<String, String> getMapByRespBody(String respons){
		  Map<String, String> map = new TreeMap<String, String>();
			String[] strs = respons.split("&");
			for(String str :strs ){
				String[] entres = str.split("=");
				map.put(entres[0], entres[1]);
			}
			return map;
	    	
	    }
	public static String signData(Map<String, String> contentData) {
		Map.Entry<String, String> obj = null;
		Map<String, String> submitFromData = new TreeMap<String, String>();
		for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext();) {
			obj = (Map.Entry<String, String>) it.next();
			String value = obj.getValue();
			if (!Strings.isNullOrEmpty(value)) {
				// 对value值进行去除前后空处理
				submitFromData.put(obj.getKey(), value.trim());
			}
		}

		String signData = coverMap2String(submitFromData) + "&signkey=" + signKey;
		String sign = Md5Utils.md5(signData.getBytes(Charsets.UTF_8));
		return sign;
	}

	public static String coverMap2String(Map<String, String> data) {
		TreeMap tree = new TreeMap();
		Iterator it = data.entrySet().iterator();

		StringBuffer sf1 = new StringBuffer();

		while (it.hasNext()) {
			Map.Entry en = (Map.Entry) it.next();
			sf1.append(en.getKey() + "=" + en.getValue() + "&");
		}

		return sf1.substring(0, sf1.length() - 1);
	}
}