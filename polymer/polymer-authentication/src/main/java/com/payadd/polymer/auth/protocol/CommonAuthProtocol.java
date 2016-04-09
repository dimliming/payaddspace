package com.payadd.polymer.auth.protocol;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.polymer.auth.constant.MessageType;
import com.payadd.polymer.auth.layer.AuthProduct;
import com.payadd.polymer.auth.layer.AuthProtocol;
import com.payadd.polymer.auth.utils.SignUtil;
import com.payadd.polymer.auth.utils.ValidatorUtil;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.MerchantMessage;
import com.payadd.polymer.model.aut.Trade;
import com.payadd.polymer.model.bdm.MerchantSecurity;
import com.payadd.polymer.model.common.RawMessage;

@ExtensionDescription(code = "common", name = "通用认证接口对接协议")
public class CommonAuthProtocol implements AuthProtocol {
	private AuthProduct product;
	/**
	 * 在调用这个方法前，应该在controller中将消息转成RawMessage对象，
	 */
	public AuthResult auth(DatabaseFacade facade, RawMessage msg) {
		Map<String, String> map = msg.getFieldMap();
		String merchantCode = map.get("merchant_code");
		String merchantTradeNo = map.get("order_no");
		
		// 3.组装MerchantMessage，报文类型设置为1，并保存到数据库中(需要保存后就直接commit)
		MerchantMessage merchantMessage = new MerchantMessage();
		Long id = null;//TODO:generate id
		merchantMessage.setId(id);
		merchantMessage.setMerchantTradeNo(merchantTradeNo);
		merchantMessage.setMsgType(MessageType.AUTH);
		merchantMessage.setMerchantCode(merchantCode);
		String reqMsg = SignUtil.coverMap2String(map);
		merchantMessage.setReqMsg(reqMsg);
		facade.insert(merchantMessage);
		
		// 1)准备好Result
		// 定义必需字段
		String[] strs = { "version", "signature", "encoding", "txn_type", "merchant_code", "order_no", "trade_time",
				"auth_type", "account_type", "account_no" };

		ArrayList<String> list = (ArrayList<String>) Arrays.asList(strs);
		AuthResult result = validate(list,msg);
		
		// 1.所有报文字段校验，如果通不过，组装错误信息到Result，返回
		if (result.getResultCode()!=null){
			merchantMessage.setRespMsg(result.getReturnMsg());
			facade.update(merchantMessage);
			
			return result;
		}
		// 2.验证签名，验证不通过就组装Result，返回
		

		SimpleQuery sq = new SimpleQuery(facade, MerchantSecurity.class);
		sq.eq("merchantCode", map.get("merchant_code"));
		MerchantSecurity mercSec = (MerchantSecurity)sq.uniqueResult();
		if (mercSec==null){
			result.setResultCode("999999");
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code="+result.getResultCode()+"&resp_msg="+result.getResultDesc());
			
			merchantMessage.setRespMsg(result.getReturnMsg());
			facade.update(merchantMessage);
			
			return result;
		}
		
		String signature = map.remove("signature");
		String signData = SignUtil.signData(map, mercSec.getSignKey());
		if (!signature.equals(signData)) {
			result.setReturnMsg("resp_code=E3&resp_msg=签名错误");
			
			merchantMessage.setRespMsg(result.getReturnMsg());
			facade.update(merchantMessage);
			
			return result;
		}
		
		//

		// 4.组装Trade，调用product.auth
		Trade trade = new Trade();
		
		trade.setMerchantCode(merchantCode);
		trade.setMerchantTradeNO(map.get("order_no"));
		trade.setAuthType(map.get("auth_type"));
		trade.setCertNo(map.get("cert_no"));
		trade.setCertType(map.get("cert_type"));
		trade.setAccountNo(map.get("account_no"));
		trade.setCustomName(map.get("custom_name"));
		trade.setPhone(map.get("phone"));
		trade.setTradeTime(Timestamp.valueOf(map.get("trade_time")));

		AuthResult productAuthResult = product.auth(facade, trade);

		productAuthResult.setResultDesc(AuthResultHelper.getDesc(productAuthResult.getResultCode()));
		productAuthResult.setReturnMsg("resp_code="+productAuthResult.getResultCode()+"&resp_msg="+productAuthResult.getResultDesc());
		
		// 8.更新MerchantMessage的反馈报文字段
		merchantMessage.setRespMsg(productAuthResult.getReturnMsg());
		facade.update(merchantMessage);
		// 7.返回Result
		return productAuthResult;
	}

	public AuthResult enquiry(DatabaseFacade facade, RawMessage msg) {
		// 定义必需字段
		String[] strs = { "version", "signature", "encoding", "txn_type", "merchant_code", "order_no" };

		// 1.所有报文字段校验，如果通不过，组装错误信息到Result，返回
		ArrayList<String> list = (ArrayList<String>) Arrays.asList(strs);
		// 1)准备好Result
		AuthResult result = validate(list,msg);
		if (result.getResultCode()!=null){
			//TODO:
			return result;
		}

		
		// 2.验证签名，验证不通过就组装Result，返回
		//TODO:
		Map<String, String> map = msg.getFieldMap();
		String signature = map.remove("signature");
		String merchantCode = map.get("merchant_code");
		String merchantTradeNo =  map.get("order_no");
		List<String> querylist = new ArrayList<String>();
		querylist.add(merchantCode);

		String signKey = (String) facade.queryOne("select sign_key from bdm_merchant_security where merchant_code=",
				querylist);
		facade.commit();
		String signData = SignUtil.signData(map, signKey);
		if (!signature.equals(signData)) {
			result.setReturnMsg("resp_code=E3&resp_msg=签名错误");
			return result;
		}
		// 3.组装MerchantMessage，报文类型设置为2，并保存到数据库中(需要保存后就直接commit)
		MerchantMessage merchantMessage = new MerchantMessage();
		//id
		//merchantTradeNo
		merchantMessage.setMsgType(MessageType.ENQUIRY);
		merchantMessage.setMerchantCode(merchantCode);
		String reqMsg = SignUtil.coverMap2String(map);
		merchantMessage.setReqMsg(reqMsg);
		facade.insert(merchantMessage);
		// 4.调用product.enquiry
		result = product.enquiry(facade, merchantCode,merchantTradeNo);
		// 5.根据返回的结果，组装反馈报文，放到Result中，
		
		// 6.更新MerchantMessage的反馈报文字段
		//TODO:set resp message
		facade.update(merchantMessage);
		// 7.返回Result
		return result;
	}
	
	private AuthResult validate(List<String> fieldList,RawMessage msg){
		
				
		AuthResult result = new AuthResult();
		Iterator<String> fieldIterator = fieldList.iterator();
		// 判断是否为空报文
		while (fieldIterator.hasNext()) {

			String key = fieldIterator.next().trim();
			String value = msg.getField(key).trim();

			switch (key) {
			case "version":
				if (!("1.0.0".equals(value))) {
					// xx字段格式错误
					result.setResultCode("");
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;
			case "signature":
				if (!ValidatorUtil.isInlength(value.trim(), 1, 1024)) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;
			case "encoding":
				if ("".equals(value)) {
					// 如果编码为空,默认为UTF-8
					msg.addField(key, "UTF-8");
				}
				break;
			case "txn_type":
				if (!"00".equals(value)) {
					// xx字段格式错误
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;
			case "merchant_code":
				if (!ValidatorUtil.isInlength(value, 32)) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				if (!(ValidatorUtil.hasLetter(value)
						&& (ValidatorUtil.hasSlash(value) || ValidatorUtil.hasDigit(value)))) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;
			case "order_no":
				if (!ValidatorUtil.isInlength(value, 6, 40)) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				if (!(ValidatorUtil.hasLetter(value)
						&& (ValidatorUtil.hasSlash(value) || ValidatorUtil.hasDigit(value)))) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;
			case "trade_time":
				if (!ValidatorUtil.isValidDate(value)) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;
			case "auth_type":
				String[] authTypes = { "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111" };
				ArrayList<String> authTypeList = (ArrayList<String>) Arrays.asList(authTypes);

				if (!authTypeList.contains(value)) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}

				if (value.charAt(0) == '1') {
					if (msg.getField("account_no") == null) {
						result.setReturnMsg("resp_code=&resp_msg=报文格式错误");
						return result;
					}
				}
				if (value.charAt(1) == '1') {
					if (msg.getField("cert_no") == null) {
						result.setReturnMsg("resp_code=&resp_msg=报文格式错误");
						return result;
					}
				}
				if (value.charAt(2) == '1') {
					if (msg.getField("custom_name") == null) {
						result.setReturnMsg("resp_code=&resp_msg=报文格式错误");
						return result;
					}
				}
				if (value.charAt(3) == '1') {
					if (msg.getField("phone") == null) {
						result.setReturnMsg("resp_code=&resp_msg=报文格式错误");
						return result;
					}
				}
				break;
			case "account_type":
				if (!"01".equals(value)) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;
			case "account_no":
				if (!ValidatorUtil.isInlength(value, 1, 60)) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				if (!(ValidatorUtil.hasLetter(value)
						&& (ValidatorUtil.hasSlash(value) || ValidatorUtil.hasDigit(value)))) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;
			case "cert_type":
				String[] certTypes = { "01", "02", "03", "04", "05", "06", "07", "99" };
				ArrayList<String> certTypeList = (ArrayList<String>) Arrays.asList(certTypes);
				if (!certTypeList.contains(value)) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;
			case "cert_no":
				if (!ValidatorUtil.isInlength(value, 1, 20)) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;
			case "custom_name":
				if (!ValidatorUtil.isInlength(value, 1, 32)) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;
			case "phone":
				if (!ValidatorUtil.isMobile(value)) {
					result.setReturnMsg("resp_code=&resp_msg=" + key + "字段格式错误");
					return result;
				}
				break;

			default:
				result.setReturnMsg("resp_code=F2&resp_msg=报文格式错误");
				return result;

			}

		}
		if (result.getResultCode()!=null){
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code="+result.getResultCode()+"&resp_msg="+result.getResultDesc());
		}
		return result;
	}

	public static void main(String[] args) {
	}
}
