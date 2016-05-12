package com.payadd.polymer.gateway.auth.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.payadd.polymer.gateway.auth.servcie.AuthService;
import com.payadd.polymer.model.common.RawMessage;

@Controller("authController")
@RequestMapping("/auth")
public class AuthController {
	
	@Resource(name="authService")
	private AuthService authService;
	
	@RequestMapping(value="index")
	public void index(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			response.setHeader("Content-Type", "text/html");
			response.getWriter().write("test");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="docking")
	public void docking(HttpServletRequest request, HttpServletResponse response, Model model){
		RawMessage msg = new RawMessage();
		setFieldValue(request,msg,"version");
		setFieldValue(request,msg,"signature");
		setFieldValue(request,msg,"encoding");
		setFieldValue(request,msg,"txn_type");
		setFieldValue(request,msg,"merchant_code");
		setFieldValue(request,msg,"order_no");
		
		String txnType = request.getParameter("txn_type");
		String result = null;
		if ("00".equals(txnType)){//认证
			setFieldValue(request,msg,"trade_time");
			setFieldValue(request,msg,"auth_type");
			setFieldValue(request,msg,"account_type");
			setFieldValue(request,msg,"account_no");
			setFieldValue(request,msg,"cert_type");
			setFieldValue(request,msg,"cert_no");
			setFieldValue(request,msg,"custom_name");
			setFieldValue(request,msg,"phone");
			setFieldValue(request,msg,"is_test");
			
			result = authService.auth(msg);
		}else if ("01".equals(txnType)){//认证查询
			result = authService.enquiry(msg);
			
		}
		
		try {
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void setFieldValue(HttpServletRequest request,RawMessage msg,String fieldName){
		String value = request.getParameter(fieldName);
		if (value!=null){
			msg.addField(fieldName, value);
		}
	}
	
}
