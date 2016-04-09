package com.payadd.polymer.auth.protocol;

import com.payadd.polymer.model.aut.AuthResult;

public class AuthResultHelper {
	public static String getDesc(String code){
		//
		return null;
	}
	public static boolean isSuccess(AuthResult result){
		if (result==null||result.getResultCode()==null||"000000".equals(result.getResultCode())){
			return true;
		}else{
			return false;
		}
	}
}
