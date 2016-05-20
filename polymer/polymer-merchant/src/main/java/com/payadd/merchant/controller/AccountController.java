package com.payadd.merchant.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.payadd.framework.common.toolkit.JsonUtil;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.polymer.base.BaseController;
import com.payadd.polymer.model.acc.Account;
import com.payadd.polymer.model.bdm.MerchantUser;

@Controller("accountController")
@RequestMapping("/account")
public class AccountController extends BaseController {
	@RequestMapping(value = "load")
	public void load(HttpServletRequest request, HttpServletResponse response, Model model) {
		MerchantUser user = getCurrentUser(request);
		String merchantCode = user.getMerchantCode();
		SimpleQuery sq = new SimpleQuery(facade, Account.class);
		sq.eq("merchantCode", merchantCode);
		Account entity = (Account) sq.uniqueResult();
		StringBuffer respMsg = new StringBuffer();
		respMsg.append("{");
		respMsg.append("\"status\":\"000000\",\"message\":\"ok\"");
		respMsg.append(",\"entity\":").append(JsonUtil.toJson(entity));
		respMsg.append("}");
		try {
			String msgStr = respMsg.toString();
			System.out.println(msgStr);
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			response.getWriter().write(msgStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private MerchantUser getCurrentUser(HttpServletRequest request) {
		MerchantUser user = (MerchantUser) request.getSession().getAttribute(LOGIN_USER);
		return user;
	}
}
