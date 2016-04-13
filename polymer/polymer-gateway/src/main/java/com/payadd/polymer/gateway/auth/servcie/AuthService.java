package com.payadd.polymer.gateway.auth.servcie;

import org.springframework.stereotype.Service;

import com.payadd.polymer.auth.layer.AuthProtocol;
import com.payadd.polymer.base.BaseService;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.common.RawMessage;

@Service("authService")
public class AuthService extends BaseService{
	private AuthProtocol protocol;
	
	public String auth(RawMessage msg){
		AuthResult result = protocol.auth(facade, msg);
		return result.getReturnMsg();
	}
	
	public String enquiry(RawMessage msg){
		AuthResult result = protocol.enquiry(facade, msg);
		return result.getReturnMsg();
	}
}
