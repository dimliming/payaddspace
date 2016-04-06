package com.payadd.polymer.gateway.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.payadd.polymer.gateway.auth.servcie.AuthService;

@Controller("authController")
@RequestMapping("/auth")
public class AuthController {
	
	@Resource(name="authService")
	private AuthService authService;
	
	@RequestMapping(value="index")
	public void index(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			response.setHeader("Content-Type", "text/html");
			response.getWriter().write(authService.auth());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
