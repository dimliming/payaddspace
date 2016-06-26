package com.payadd.merchant.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.payadd.framework.common.toolkit.IdGenerator;
import com.payadd.framework.common.toolkit.JsonUtil;
import com.payadd.polymer.base.BaseController;
import com.payadd.polymer.model.acc.Account;
import com.payadd.polymer.model.bdm.Merchant;
import com.payadd.polymer.model.bdm.MerchantUser;
import com.payadd.polymer.model.sys.User;

@Controller("merchantUserController")
@RequestMapping("/merchantUser")
public class MerchantUserController extends BaseController {

	@RequestMapping(value = "load")
	public void load(HttpServletRequest request, HttpServletResponse response, Model model) {
		MerchantUser entity = getCurrentUser(request);
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

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, MerchantUser entity) {

		facade.update(entity);
		request.getSession().setAttribute(LOGIN_USER, entity);
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
