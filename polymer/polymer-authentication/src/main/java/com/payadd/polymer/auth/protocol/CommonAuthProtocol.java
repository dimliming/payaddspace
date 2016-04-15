package com.payadd.polymer.auth.protocol;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.common.extension.Multiple;
import com.payadd.framework.common.toolkit.IdGenerator;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.polymer.auth.constant.MessageFields;
import com.payadd.polymer.auth.constant.MessageType;
import com.payadd.polymer.auth.constant.SystemRespCode;
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
		String merchantCode = map.get(MessageFields.MERCHANT_CODE);
		String merchantTradeNo = map.get(MessageFields.ORDER_NO);

		// 3.组装MerchantMessage，报文类型设置为1，并保存到数据库中(需要保存后就直接commit)
		MerchantMessage merchantMessage = new MerchantMessage();
		Long id = IdGenerator.nextLongSequence(MerchantMessage.class);
		merchantMessage.setId(id);
		merchantMessage.setMerchantTradeNo(merchantTradeNo);
		merchantMessage.setMsgType(MessageType.AUTH);
		merchantMessage.setMerchantCode(merchantCode);
		String reqMsg = SignUtil.coverMap2String(map);
		merchantMessage.setReqMsg(reqMsg);
		facade.insert(merchantMessage);

		// 定义必需字段

		List<String> list =  Arrays.asList(MessageFields.AUTH_FIELDS);
		AuthResult result = validate(list, msg);

		// 1.所有报文字段校验，如果通不过，组装错误信息到Result，返回
		if (result.getResultCode() != null) {
			merchantMessage.setRespMsg(result.getReturnMsg());
			facade.update(merchantMessage);

			return result;
		}
		// 2.验证签名，验证不通过就组装Result，返回
		// 得到商户signKey
		SimpleQuery sq = new SimpleQuery(facade, MerchantSecurity.class);
		sq.eq("merchantCode", map.get(MessageFields.MERCHANT_CODE));
		MerchantSecurity mercSec = (MerchantSecurity) sq.uniqueResult();
		if (mercSec == null) {
			result.setResultCode(SystemRespCode.OPERATE_EXCEPTION);
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());

			merchantMessage.setRespMsg(result.getReturnMsg());
			facade.update(merchantMessage);

			return result;
		}

		String signature = map.remove(MessageFields.SIGNATURE);
		String signData = SignUtil.signData(map, mercSec.getSignKey());
		if (!signature.equals(signData)) {
			result.setResultCode(SystemRespCode.SIGNATURE_ERR);
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());

			merchantMessage.setRespMsg(result.getReturnMsg());
			facade.update(merchantMessage);

			return result;
		}

		//

		// 4.组装Trade，调用product.auth
		Trade trade = new Trade();

		trade.setMerchantCode(merchantCode);
		trade.setMerchantTradeNO(map.get(MessageFields.ORDER_NO));
		trade.setAuthType(map.get(MessageFields.AUTH_TYPE));
		trade.setCertNo(map.get(MessageFields.CERT_NO));
		trade.setCertType(map.get(MessageFields.CERT_TYPE));
		trade.setAccountNo(map.get(MessageFields.ACCOUNT_NO));
		trade.setCustomName(map.get(MessageFields.CUSTOM_NAME));
		trade.setPhone(map.get(MessageFields.PHONE));
		trade.setTradeTime(Timestamp.valueOf(map.get(MessageFields.TRADE_TIME)));
		trade.setIsTest(map.get("is_test"));

		AuthResult productAuthResult = product.auth(facade, trade);

		productAuthResult.setResultDesc(AuthResultHelper.getDesc(productAuthResult.getResultCode()));
		productAuthResult.setReturnMsg(
				"resp_code=" + productAuthResult.getResultCode() + "&resp_msg=" + productAuthResult.getResultDesc());

		// 8.更新MerchantMessage的反馈报文字段
		merchantMessage.setRespMsg(productAuthResult.getReturnMsg());
		facade.update(merchantMessage);
		// 7.返回Result
		return productAuthResult;
	}

	public AuthResult enquiry(DatabaseFacade facade, RawMessage msg) {
		MerchantMessage merchantMessage = new MerchantMessage();
		// 1.所有报文字段校验，如果通不过，组装错误信息到Result，返回
		List<String> list =  Arrays.asList(MessageFields.ENQUIRY_FIELDS);
		// 1)准备好Result
		AuthResult result = validate(list, msg);
		if (result.getResultCode() != null) {
			// TODO:
			merchantMessage.setRespMsg(result.getReturnMsg());
			facade.update(merchantMessage);

			return result;
		}
		// 2.验证签名，验证不通过就组装Result，返回
		// TODO:
		Map<String, String> map = msg.getFieldMap();
		String signature = map.remove(MessageFields.SIGNATURE);
		String merchantCode = map.get(MessageFields.MERCHANT_CODE);
		String merchantTradeNo = map.get(MessageFields.ORDER_NO);
		List<String> querylist = new ArrayList<String>();
		querylist.add(merchantCode);

		SimpleQuery sq = new SimpleQuery(facade, MerchantSecurity.class);
		sq.eq("merchantCode", map.get(MessageFields.MERCHANT_CODE));
		MerchantSecurity mercSec = (MerchantSecurity) sq.uniqueResult();
		if (mercSec == null) {
			result.setResultCode(SystemRespCode.OPERATE_EXCEPTION);
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());

			merchantMessage.setRespMsg(result.getReturnMsg());
			facade.update(merchantMessage);

			return result;
		}
		String signData = SignUtil.signData(map, mercSec.getSignKey());
		if (!signature.equals(signData)) {
			result.setResultCode(SystemRespCode.SIGNATURE_ERR);
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());

			merchantMessage.setRespMsg(result.getReturnMsg());
			facade.update(merchantMessage);
			return result;
		}
		// 3.组装MerchantMessage，报文类型设置为2，并保存到数据库中(需要保存后就直接commit)
		// id
		merchantMessage.setMerchantTradeNo(merchantTradeNo);
		merchantMessage.setMsgType(MessageType.ENQUIRY);
		merchantMessage.setMerchantCode(merchantCode);
		String reqMsg = SignUtil.coverMap2String(map);
		merchantMessage.setReqMsg(reqMsg);
		facade.insert(merchantMessage);
		// 4.调用product.enquiry
		result = product.enquiry(facade, merchantCode, merchantTradeNo);
		// 5.根据返回的结果，组装反馈报文，放到Result中，
		result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
		result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());
		// 6.更新MerchantMessage的反馈报文字段
		merchantMessage.setRespMsg(result.getReturnMsg());
		facade.update(merchantMessage);
		// 7.返回Result
		return result;
	}

	private AuthResult validate(List<String> fieldList, RawMessage msg) {

		AuthResult result = new AuthResult();
		Iterator<String> fieldIterator = fieldList.iterator();
		if (!fieldIterator.hasNext()) {
			result.setResultCode(SystemRespCode.MESSAGE_NULL);
			return result;
		}
		while (fieldIterator.hasNext()) {

			String key = fieldIterator.next().trim();
			String value = msg.getField(key).trim();

			switch (key) {
			case MessageFields.VERSION:
				if (!("1.0.0".equals(value))) {
					// xx字段格式错误
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				break;
			case MessageFields.SIGNATURE:
				if (!ValidatorUtil.isInlength(value.trim(), 1, 1024)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				break;
			case MessageFields.ENCODING:
				if ("".equals(value)) {
					// 如果编码为空,默认为UTF-8
					msg.addField(key, "UTF-8");
				}
				break;
			case MessageFields.TXN_TYPE:
				if (!("00".equals(value) || "01".equals(value))) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				break;
			case MessageFields.MERCHANT_CODE:
				if (!ValidatorUtil.isInlength(value, 1,32)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				/*if (ValidatorUtil.hasLetter(value)|| ValidatorUtil.hasSlash(value)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}*/
				break;
			case MessageFields.ORDER_NO:
				if (!ValidatorUtil.isInlength(value, 6, 40)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				break;
			case MessageFields.TRADE_TIME:
				if (!ValidatorUtil.isValidDate(value)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				break;
			case MessageFields.AUTH_TYPE:
				String[] authTypes = { "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111" };
				List<String> authTypeList =  Arrays.asList(authTypes);

				if (!authTypeList.contains(value)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}

				if (value.charAt(0) == '1') {
					if (msg.getField(MessageFields.ACCOUNT_NO) == null) {
						result.setResultCode(SystemRespCode.MESSAGE_ERR);
						return result;
					}
				}
				if (value.charAt(1) == '1') {
					if (msg.getField(MessageFields.CERT_NO) == null) {
						result.setResultCode(SystemRespCode.MESSAGE_ERR);
					}
				}
				if (value.charAt(2) == '1') {
					if (msg.getField(MessageFields.CUSTOM_NAME) == null) {
						result.setResultCode(SystemRespCode.MESSAGE_ERR);
					}
				}
				if (value.charAt(3) == '1') {
					if (msg.getField(MessageFields.PHONE) == null) {
						result.setResultCode(SystemRespCode.MESSAGE_ERR);
					}
				}
				break;
			case MessageFields.ACCOUNT_TYPE:
				if (!"01".equals(value)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				break;
			case MessageFields.ACCOUNT_NO:
				if (!ValidatorUtil.isInlength(value, 1, 60)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				if (ValidatorUtil.hasLetter(value)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				break;
			case MessageFields.CERT_TYPE:
				String[] certTypes = { "01", "02", "03", "04", "05", "06", "07", "99" };
				List<String> certTypeList =  Arrays.asList(certTypes);
				if (!certTypeList.contains(value)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				break;
			case MessageFields.CERT_NO:
				if (!ValidatorUtil.isInlength(value, 1, 20)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				break;
			case MessageFields.CUSTOM_NAME:
				if (!ValidatorUtil.isInlength(value, 1, 32)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				break;
			case MessageFields.PHONE:
				if (!ValidatorUtil.isMobile(value)) {
					result.setResultCode(SystemRespCode.FIELD_FORMAT_ERR);
				}
				break;

			default:
				result.setResultCode(SystemRespCode.MESSAGE_ERR);

			}

			if (result.getResultCode() != null) {
				result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
				if (SystemRespCode.FIELD_FORMAT_ERR.equals(result.getResultCode())) {
					result.setReturnMsg(
							"resp_code=" + result.getResultCode() + "&resp_msg=" + key + result.getResultDesc());
				} else {

					result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());
				}

				return result;
			}
		}

		return result;
	}

	public static void main(String[] args) {
		Multiple m = AuthProtocol.class.getAnnotation(Multiple.class);
		if (m!=null){
			System.out.println("have annonation");
		}else{
			System.out.println("didn't have annonation");
		}
	}
}
