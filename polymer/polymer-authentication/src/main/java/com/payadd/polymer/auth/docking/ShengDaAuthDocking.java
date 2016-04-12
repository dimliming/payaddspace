package com.payadd.polymer.auth.docking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.polymer.auth.constant.MessageFields;
import com.payadd.polymer.auth.constant.SystemRespCode;
import com.payadd.polymer.auth.layer.AuthDocking;
import com.payadd.polymer.auth.protocol.AuthResultHelper;
import com.payadd.polymer.auth.utils.HttpClient;
import com.payadd.polymer.auth.utils.SignUtil;
import com.payadd.polymer.model.aut.AuthDockingConfig;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.Trade;
import com.payadd.polymer.model.bdm.MerchantSecurity;

@ExtensionDescription(code = "shengda", name = "盛大认证接口")
public class ShengDaAuthDocking implements AuthDocking {

	public AuthResult auth(DatabaseFacade facade, Trade trade, AuthDockingConfig config) {
		AuthResult result = new AuthResult();
		SimpleQuery simpleQuery = new SimpleQuery(facade, MerchantSecurity.class);
		simpleQuery.eq("merchantCode", trade.getMerchantCode());
		MerchantSecurity mercSec = (MerchantSecurity) simpleQuery.uniqueResult();
		String signKey = mercSec.getSignKey();
		// 1.组装认证报文
		Map<String, String> contentData = new TreeMap<String, String>();
		contentData.put("version", "5.0.0");
		contentData.put("encoding", "UTF-8");
		// 00 认证
		contentData.put("txnType", "00");
		contentData.put("merId", trade.getMerchantCode());
		// defaultPayType 位图形式
		// 第一位数字是1代表卡号，第二为数字1代表验证身份证，第三位为数字1代表验证姓名，第四位为数字１代表验证手机
		contentData.put("defaultPayType", trade.getAuthType());
		contentData.put("orderId", String.valueOf(System.currentTimeMillis()));
		contentData.put("txnTime",
				new SimpleDateFormat("yyyyMMddhhmmss").format(new Date(trade.getTradeTime().getTime())));
		contentData.put("accNo", trade.getAccountNo());
		// 01 卡
		contentData.put("accType", "01");
		// 01身份证
		contentData.put("certifTp", trade.getCertType());
		contentData.put("certifId", trade.getCertNo());
		contentData.put("customerNm", trade.getCustomName());
		contentData.put("phoneNo", trade.getPhone());
		System.out.println(contentData);
		String sign = SignUtil.signData(contentData, signKey);
		// 2.签名
		contentData.put("signature", sign);
		// 3.发送报文
		HttpClient client = new HttpClient(config.getAuthURL(), 6000, 60000);
		try {
			client.send(contentData, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 4.获取返回报文，解析，验签，验签不通过，组装错误信息返回
		String respons = client.getResult();
		Map<String, String> responsMap = SignUtil.getMapByRespBody(respons);
		SimpleQuery sq = new SimpleQuery(facade, MerchantSecurity.class);
		String signature = responsMap.remove(MessageFields.SIGNATURE);
		String signData = SignUtil.signData(responsMap, mercSec.getSignKey());
		if (!signature.equals(signData)) {
			result.setResultCode(SystemRespCode.SIGNATURE_ERR);
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());
			return result;
		}
		// 8.根据返回的结果，更新channel_trade_no、channel_code、resp_code、resp_msg到trade
		// TODO:channel_code
		trade.setChannelTradeNo(responsMap.get("orderId"));
		trade.setRespCode(responsMap.get("respCode"));
		trade.setRespMsg("respMsg");

		// 5.记录ChannelMessage日志信息
		// TODO:记录ChannelMessage日志信息
		// 6.获取认证结果
		// TODO:获取认证结果
		// 7.将结果封装到Result，返回
		// TODO:将结果封装到Result，返回
		return result;
	}

	public AuthResult enquiry(DatabaseFacade facade, Trade trade, AuthDockingConfig config) {
		AuthResult result = new AuthResult();
		SimpleQuery simpleQuery = new SimpleQuery(facade, MerchantSecurity.class);
		simpleQuery.eq("merchantCode", trade.getMerchantCode());
		MerchantSecurity mercSec = (MerchantSecurity) simpleQuery.uniqueResult();
		String signKey = mercSec.getSignKey();
		// 1.组装查询报文
		Map<String, String> contentData = new TreeMap<String, String>();
		contentData.put("version", "5.0.0");
		contentData.put("encoding", "UTF-8");

		// 01 认证订单查询
		contentData.put("txnType", "01");
		contentData.put("merId", trade.getMerchantCode());
		// 商户原交易订单号
		contentData.put("orderId", trade.getTradeNo());
		// 商户原交易订单时间
		contentData.put("txnTime",
				new SimpleDateFormat("yyyyMMddhhmmss").format(new Date(trade.getTradeTime().getTime())));
		String sign = SignUtil.signData(contentData, signKey);

		// 2.签名
		contentData.put("signature", sign);
		// 3.发送报文
		HttpClient client = new HttpClient("http://127.0.0.1:8080/auth.htm", 6000, 60000);
		try {
			client.send(contentData, "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String respons = client.getResult();
		// 4.获取返回报文，解析，验签，验签不通过，组装错误信息返回
		Map<String, String> responsMap = SignUtil.getMapByRespBody(respons);
		SimpleQuery sq = new SimpleQuery(facade, MerchantSecurity.class);
		String signature = responsMap.remove(MessageFields.SIGNATURE);
		String signData = SignUtil.signData(responsMap, mercSec.getSignKey());
		if (!signature.equals(signData)) {
			result.setResultCode(SystemRespCode.SIGNATURE_ERR);
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());
			return result;
		}
		// 5.记录ChannelMessage日志信息
		// TODO:记录ChannelMessage日志信息
		// 6.获取认证结果
		// TODO:获取认证结果
		// 7.将结果封装到Result，返回
		// TODO:将结果封装到Result，返回
		return result;
	}

}
