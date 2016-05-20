package com.payadd.polymer.auth.docking;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.common.toolkit.IdGenerator;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.auth.constant.MessageFields;
import com.payadd.polymer.auth.constant.MessageType;
import com.payadd.polymer.auth.constant.SystemRespCode;
import com.payadd.polymer.auth.layer.AuthDocking;
import com.payadd.polymer.auth.protocol.AuthResultHelper;
import com.payadd.polymer.auth.utils.HttpClient;
import com.payadd.polymer.auth.utils.SignUtil;
import com.payadd.polymer.model.aut.AuthDockingConfig;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.ChannelMessage;
import com.payadd.polymer.model.aut.Trade;

@ExtensionDescription(code = "shengda", name = "盛大认证接口")
public class ShengDaAuthDocking implements AuthDocking {

	public AuthResult auth(DatabaseFacade facade, Trade trade, AuthDockingConfig config) {
		AuthResult result = new AuthResult();
		System.out.println(config.getSubMerchantCode());
		// 1.组装认证报文
		Map<String, String> contentData = new TreeMap<String, String>();
		contentData.put("version", "5.0.0");
		contentData.put("encoding", "UTF-8");
		// 00 认证
		contentData.put("txnType", "00");
		contentData.put("merId", config.getSubMerchantCode());
		// defaultPayType 位图形式
		// 第一位数字是1代表卡号，第二为数字1代表验证身份证，第三位为数字1代表验证姓名，第四位为数字１代表验证手机
		contentData.put("defaultPayType", trade.getAuthType());
		contentData.put("orderId", String.valueOf(System.currentTimeMillis()));
		contentData.put("txnTime",
				new SimpleDateFormat("yyyyMMddhhmmss").format(new Date(trade.getTradeTime().getTime())));
		if(trade.getAccountNo()!=null&&(!trade.getAccountNo().equals(""))){
			contentData.put("accNo", trade.getAccountNo());
		}
		if(trade.getCertNo()!=null&&(!trade.getCertNo().equals(""))){
			contentData.put("certifId", trade.getCertNo());
		}
		if(trade.getCustomName()!=null&&(!trade.getCustomName().equals(""))){
			contentData.put("customerNm", trade.getCustomName());
		}
		if(trade.getPhone()!=null&&(!trade.getPhone().equals(""))){
			contentData.put("phoneNo", trade.getPhone());
		}
		
		
		// 01 卡
		contentData.put("accType", "01");
		// 01身份证
		if(trade.getCertType()!=null){
			
		}
		contentData.put("certifTp", trade.getCertType());
		String sign = SignUtil.signData(contentData, config.getMd5Key());
		// 2.签名
		contentData.put("signature", sign);
		System.out.println(contentData);
		// 3.发送报文
		HttpClient client = new HttpClient(config.getAuthURL(), 6000, 60000);
		String reqMsg = getRequestParamString(contentData, "utf-8");
		try {
			client.send(reqMsg, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 4.获取返回报文，解析，验签，验签不通过，组装错误信息返回
		String respons = client.getResult();
		String str = null;
		
		try {
			str = URLDecoder.decode(respons, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> responsMap = SignUtil.getMapByRespBody(str);
		String signature = responsMap.remove(MessageFields.SIGNATURE);
		String signData = SignUtil.signData(responsMap, config.getMd5Key());
		if (!signature.equals(signData)) {
			result.setResultCode(SystemRespCode.SIGNATURE_ERR);
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());
			return result;
		}
		// 8.根据返回的结果，更新channel_trade_no、channel_code、resp_code、resp_msg到trade
		trade.setChannelTradeNo(responsMap.get("orderId"));
		trade.setRespCode(RespCodeUtils.respCodeTranslate(responsMap.get("respCode")));
		trade.setRespMsg(responsMap.get("respMsg"));

		// 5.记录ChannelMessage日志信息
		ChannelMessage channelMessage = new ChannelMessage();
		channelMessage.setId(IdGenerator.nextLongSequence(ChannelMessage.class));
		channelMessage.setChannelCode("shengda");
		channelMessage.setMerchantCode(trade.getMerchantCode());
		channelMessage.setMsgType(MessageType.AUTH);
		channelMessage.setReqMsg(reqMsg);
		channelMessage.setRespMsg(respons);
		channelMessage.setTradeNo(trade.getTradeNo());
		facade.insert(channelMessage);


		// 7.将结果封装到Result，返回
		result.setResultCode(trade.getRespCode());
		return result;
	}

	public AuthResult enquiry(DatabaseFacade facade, Trade trade, AuthDockingConfig config) {
		AuthResult result = new AuthResult();
		// 获取商户trade的信息
		// 1.组装查询报文
		Map<String, String> contentData = new TreeMap<String, String>();
		contentData.put("version", "5.0.0");
		contentData.put("encoding", "UTF-8");

		// 01 认证订单查询
		contentData.put("txnType", "01");
		contentData.put("merId", config.getSubMerchantCode());
		// 商户原交易订单号
		contentData.put("orderId", trade.getChannelTradeNo());
		// 商户原交易订单时间
		contentData.put("txnTime",
				new SimpleDateFormat("yyyyMMddhhmmss").format(new Date(trade.getTradeTime().getTime())));
		String sign = SignUtil.signData(contentData, config.getMd5Key());
		// 2.签名
		contentData.put("signature", sign);
		// 3.发送报文
		HttpClient client = new HttpClient(config.getEnquiryURL(), 6000, 60000);
		String reqMsg = getRequestParamString(contentData, "utf-8");
		System.out.println(reqMsg);
		try {
			client.send(reqMsg, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String respons = client.getResult();
		// 4.获取返回报文，解析，验签，验签不通过，组装错误信息返回
		Map<String, String> responsMap = SignUtil.getMapByRespBody(respons);
		String signature = responsMap.remove(MessageFields.SIGNATURE);
		String signData = SignUtil.signData(responsMap, config.getMd5Key());
		if (!signature.equals(signData)) {
			result.setResultCode(SystemRespCode.SIGNATURE_ERR);
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());
			return result;
		}
		// 5.记录ChannelMessage日志信息
		ChannelMessage channelMessage = new ChannelMessage();
		channelMessage.setId(IdGenerator.nextLongSequence(ChannelMessage.class));
		channelMessage.setChannelCode("shengda");
		channelMessage.setMerchantCode(trade.getMerchantCode());
		channelMessage.setMsgType(MessageType.ENQUIRY);
		channelMessage.setReqMsg(reqMsg);
		channelMessage.setRespMsg(respons);
		channelMessage.setTradeNo(trade.getTradeNo());
		facade.insert(channelMessage);

		// 7.将结果封装到Result，返回
		result.setResultCode(RespCodeUtils.respCodeTranslate(trade.getRespCode()));
		return result;
	}

	private String getRequestParamString(Map<String, String> requestParam, String coder) {
		if (null == coder || "".equals(coder)) {
			coder = "UTF-8";
		}

		StringBuffer sf = new StringBuffer("");
		String reqstr = "";
		if (null != requestParam && 0 != requestParam.size()) {
			Iterator i$ = requestParam.entrySet().iterator();

			while (i$.hasNext()) {
				Entry en = (Entry) i$.next();

				try {
					sf.append((String) en.getKey() + "=" + (null != en.getValue() && !"".equals(en.getValue())
							? URLEncoder.encode((String) en.getValue(), coder) : "") + "&");
				} catch (UnsupportedEncodingException var8) {
					var8.printStackTrace();
					return "";
				}
			}

			reqstr = sf.substring(0, sf.length() - 1);
		}
		System.out.println("请求报文:[" + reqstr + "]");
		return reqstr;
	}



}
