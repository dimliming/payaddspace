package com.payadd.polymer.auth.docking;

import com.payadd.polymer.auth.constant.SystemRespCode;

public class RespCodeUtils {
	public static String respCodeTranslate(String channelRespCode) {
		String respCode = null;
		switch (channelRespCode) {
		case "00":
			respCode = SystemRespCode.SUCCESS;
			break;
		case "E1":
			respCode = SystemRespCode.MESSAGE_NULL;
			break;
		case "E2":
			respCode = SystemRespCode.SYSTEM_EXCEPTION;
			break;
		case "E3":
			respCode = SystemRespCode.SIGNATURE_ERR;
			break;
		case "F2":
			respCode = SystemRespCode.MESSAGE_ERR;
			break;
		case "E4":
			respCode = SystemRespCode.MERCHANT_ERR;
			break;
		case "E5":
			respCode = SystemRespCode.BALANCE_NO_ENOUGH;
			break;
		case "E6":
			respCode = SystemRespCode.CARD_BIN_ERR;
			break;
		case "E7":
			respCode = SystemRespCode.MERCHANT_CONFIG_NULL;
			break;
		case "E8":
			respCode = SystemRespCode.CHANNAL_ERR;
			break;
		case "E9":
			respCode = SystemRespCode.MERCHANT_TRADE_NO_REPEAT;
			break;
		case "F1":
			respCode = SystemRespCode.MERCHANT_TRADE_NULL;
			break;
		case "01":
			respCode = SystemRespCode.CALL_BANK;
			break;
		case "04":
			respCode = SystemRespCode.CONFISCATE_CARD;
			break;
		case "05":
			respCode = SystemRespCode.MASTER_AUTH_FAIL;
			break;
		case "12":
			respCode = SystemRespCode.TRADE_INVALID;
			break;
		case "14":
			respCode = SystemRespCode.CARD_INVALID;
			break;
		case "21":
			respCode = SystemRespCode.CARD_SLEEP;
			break;
		case "34":
			respCode = SystemRespCode.CARD_CHEAT;
			break;
		case "40":
			respCode = SystemRespCode.TRADE_TYPE_NONSUPP;
			break;
		case "41":
			respCode = SystemRespCode.CARD_LOSS;
			break;
		case "51":
			respCode = SystemRespCode.CAPITAL_NO_ENOUGH;
			break;
		case "54":
			respCode = SystemRespCode.CARD_PAST_DUE;
			break;
		case "55":
			respCode = SystemRespCode.PASSWORD_ERR;
			break;
		case "57":
			respCode = SystemRespCode.CARD_TRADE_NO_ALLOW;
			break;
		case "61":
			respCode = SystemRespCode.TRADE_MONEY_EXCEED;
			break;
		case "62":
			respCode = SystemRespCode.CARD_LIMIT;
			break;
		case "75":
			respCode = SystemRespCode.PASSWORD_ERR_EXCEED;
			break;
		case "91":
			respCode = SystemRespCode.BANK_ERR;
			break;
		case "98":
			respCode = SystemRespCode.BANK_OVERTIME;
			break;
		case "ER":
			respCode = SystemRespCode.OPERATE_EXCEPTION;
			break;

		default:
			respCode = SystemRespCode.FIELD_FORMAT_ERR;
			break;
		}
		return respCode;
	}
}
