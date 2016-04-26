package com.payadd.admin.common.sys.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.payadd.polymer.base.BaseController;

public class UserInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if (request.getSession().getAttribute(BaseController.LOGIN_USER)!=null){
			return true;
		}else{
			String   respMsg = "{\"status\":\"100001\",\"message\":\"尚未登录或系统超时！\"}";
			
			try {
				System.out.println(respMsg);
				response.setHeader("Content-Type", "text/html;charset=utf-8");
				response.getWriter().write(respMsg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	}
}
