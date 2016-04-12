package com.payadd.polymer.auth.protocol;

import com.payadd.polymer.auth.constant.SystemRespCode;
import com.payadd.polymer.model.aut.AuthResult;

public class AuthResultHelper {
	public static String getDesc(String code) {
		String resultDesc = null;
		switch (code) {
		case SystemRespCode.BALANCE_NO_ENOUGH:
			resultDesc = "商户余额不足";
			break;
		case SystemRespCode.BANK_ERR:
			resultDesc = "发卡方状态不正常，请稍后重试";
			break;

		case SystemRespCode.BANK_OVERTIME:
			resultDesc = "发卡方超时";
			break;

		case SystemRespCode.CALL_BANK:
			resultDesc = "请持卡人与发卡银行联系";
			break;

		case SystemRespCode.CAPITAL_NO_ENOUGH:
			resultDesc = "资金不足";
			break;

		case SystemRespCode.CARD_BIN_ERR:
			resultDesc = "卡BIN错误";
			break;

		case SystemRespCode.CARD_CHEAT:
			resultDesc = "作弊卡,呑卡";
			break;

		case SystemRespCode.CARD_INVALID:
			resultDesc = "无效卡号";
			break;

		case SystemRespCode.CARD_LIMIT:
			resultDesc = "受限制的卡";
			break;

		case SystemRespCode.CARD_LOSS:
			resultDesc = "挂失卡，请没收（POS）";
			break;

		case SystemRespCode.CARD_PAST_DUE:
			resultDesc = "该卡已过期";
			break;

		case SystemRespCode.CARD_SLEEP:
			resultDesc = "该卡未初始化或睡眠卡";
			break;

		case SystemRespCode.CARD_TRADE_NO_ALLOW:
			resultDesc = "不允许此卡交易";
			break;

		case SystemRespCode.CHANNAL_ERR:
			resultDesc = "无可用通道";
			break;

		case SystemRespCode.CONFISCATE_CARD:
			resultDesc = "没收卡";
			break;

		case SystemRespCode.FIELD_FORMAT_ERR:
			resultDesc = "字段格式错误";
			break;
		case SystemRespCode.MASTER_AUTH_FAIL:
			resultDesc = "持卡人认证失败";
			break;
		case SystemRespCode.MERCHANT_CONFIG_NULL:
			resultDesc = "商户配置为空";
			break;
		case SystemRespCode.MERCHANT_ERR:
			resultDesc = "商户状态异常或商户不存在";
			break;
		case SystemRespCode.MERCHANT_TRADE_NO_REPEAT:
			resultDesc = "商户订单号重复";
			break;
		case SystemRespCode.MERCHANT_TRADE_NULL:
			resultDesc = "商户订单不存在";
			break;
		case SystemRespCode.MESSAGE_ERR:
			resultDesc = "报文格式错误";
			break;
		case SystemRespCode.MESSAGE_NULL:
			resultDesc = "报文为空";
			break;
		case SystemRespCode.OPERATE_EXCEPTION:
			resultDesc = "业务异常";
			break;
		case SystemRespCode.PASSWORD_ERR:
			resultDesc = "密码错误";
			break;
		case SystemRespCode.PASSWORD_ERR_EXCEED:
			resultDesc = "密码错误次数超限";
			break;
		case SystemRespCode.SIGNATURE_ERR:
			resultDesc = "签名错误";
			break;
		case SystemRespCode.SUCCESS:
			resultDesc = "交易成功";
			break;
		case SystemRespCode.SYSTEM_EXCEPTION:
			resultDesc = "系统异常";
			break;
		case SystemRespCode.TRADE_INVALID:
			resultDesc = "无效交易";
			break;
		case SystemRespCode.TRADE_MONEY_EXCEED:
			resultDesc = "交易金额超限";
			break;
		case SystemRespCode.TRADE_TYPE_NONSUPP:
			resultDesc = "发卡方不支持的交易类型";
			break;

		default:
			break;
		}
		// switch根据code匹配出Desc
		return resultDesc;
	}

	public static boolean isSuccess(AuthResult result) {
		if (result == null || result.getResultCode() == null || "000000".equals(result.getResultCode())) {
			return true;
		} else {
			return false;
		}
	}
}
