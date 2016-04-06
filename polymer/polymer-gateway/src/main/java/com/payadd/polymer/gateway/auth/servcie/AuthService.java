package com.payadd.polymer.gateway.auth.servcie;

import org.springframework.stereotype.Service;

import com.payadd.polymer.auth.layer.AuthProtocol;

@Service("authService")
public class AuthService {
	private AuthProtocol protocol;
	
	public String auth(){
		return "return from server";
	}
}
