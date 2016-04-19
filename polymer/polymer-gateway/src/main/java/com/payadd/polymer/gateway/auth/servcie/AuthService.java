package com.payadd.polymer.gateway.auth.servcie;

import java.lang.reflect.Field;

import org.springframework.stereotype.Service;

import com.payadd.framework.common.exception.SystemException;
import com.payadd.polymer.auth.layer.AuthProtocol;
import com.payadd.polymer.base.BaseService;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.common.RawMessage;

@Service("authService")
public class AuthService extends BaseService{
	private AuthProtocol protocol;
	
	public String auth(RawMessage msg){
		AuthResult result = protocol.auth(facade, msg);
		boolean b = true;
		if (b){
			throw new SystemException("1111");
		}
		return result.getReturnMsg();
	}
	
	public String enquiry(RawMessage msg){
		AuthResult result = protocol.enquiry(facade, msg);
		return result.getReturnMsg();
	}
	
	public static void main(String[] args){
		Field[] fields = AuthService.class.getDeclaredFields();
		for (int i=0;i<fields.length;i++){
			Field field = fields[i];
			Class<?>[] implInterfaces = field.getType().getInterfaces();
			System.out.println(implInterfaces[0].getName());
		}
	}
}
