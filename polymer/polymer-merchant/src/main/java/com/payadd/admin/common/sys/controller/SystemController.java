package com.payadd.admin.common.sys.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.payadd.framework.common.toolkit.JsonUtil;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.polymer.base.BaseController;
import com.payadd.polymer.model.bdm.Merchant;
import com.payadd.polymer.model.bdm.MerchantUser;
import com.payadd.polymer.model.sys.User;

@Controller("systemController")
@RequestMapping("/system")
public class SystemController extends BaseController{
	@RequestMapping(value="login")
	public void login(HttpServletRequest request, HttpServletResponse response, Model model){
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		System.out.println("loginName="+loginName);
		
		SimpleQuery sq = new SimpleQuery(facade,MerchantUser.class);
		sq.eq("loginName", loginName);
		sq.eq("password", password);
		MerchantUser user = (MerchantUser)sq.uniqueResult();
		
		String respMsg = null;
		if (user!=null){
			request.getSession().setAttribute(LOGIN_USER, user);
			respMsg = "{\"status\":\"000000\",\"message\":\"ok\",\"user\":"+JsonUtil.toJson(user)+"}";
			
		}else{
			respMsg = "{\"status\":\"100002\",\"message\":\"用户名或密码错误！\"}";
		}
		try {
			System.out.println(respMsg);
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			response.getWriter().write(respMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
