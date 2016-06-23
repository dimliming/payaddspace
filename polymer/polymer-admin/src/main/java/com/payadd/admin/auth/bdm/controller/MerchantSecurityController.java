package com.payadd.admin.auth.bdm.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.payadd.admin.auth.bdm.service.MerchantSecurityService;
import com.payadd.admin.utils.Md5Utils;
import com.payadd.framework.common.toolkit.IdGenerator;
import com.payadd.framework.common.toolkit.JsonUtil;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.polymer.base.BaseController;
import com.payadd.polymer.model.bdm.MerchantSecurity;
import com.payadd.polymer.model.sys.User;

@Controller("merchantSecurityController")
@RequestMapping("/merchantSecurity")
public class MerchantSecurityController extends BaseController {
	@Resource(name = "merchantSecurityService")
	private MerchantSecurityService merchantSecurityService;

	@RequestMapping(value = "load")
	public void load(HttpServletRequest request, HttpServletResponse response, Model model) {
		String merchantCode = request.getParameter("merchantCode");
		System.out.println(merchantCode);
		SimpleQuery sq = new SimpleQuery(facade, MerchantSecurity.class);
		sq.eq("merchantCode", merchantCode);
		MerchantSecurity entity = (MerchantSecurity) sq.uniqueResult();
		if (entity == null) {
			entity = new MerchantSecurity();
			entity.setMerchantCode(merchantCode);
			entity.setId(IdGenerator.nextLongSequence(MerchantSecurity.class));
			entity.setProtocolCode("common");
			facade.insert(entity);
		}
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
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, MerchantSecurity entity) {
		entity.setSignKey(Md5Utils.string2MD5(new Timestamp(System.currentTimeMillis()).toString()));
		merchantSecurityService.update(entity);
		
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

	private User getCurrentUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGIN_USER);
		return user;
	}
}
