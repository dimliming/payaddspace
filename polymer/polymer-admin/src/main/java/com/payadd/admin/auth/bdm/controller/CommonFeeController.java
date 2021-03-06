package com.payadd.admin.auth.bdm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.payadd.framework.common.toolkit.JsonUtil;
import com.payadd.framework.ddl.query.PaginationQuery;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.polymer.base.BaseController;
import com.payadd.polymer.model.aut.CommonFee;
import com.payadd.polymer.model.bdm.Merchant;
import com.payadd.polymer.model.bdm.MerchantSecurity;

@Controller("commonFeeController")
@RequestMapping("/commonFee")
public class CommonFeeController extends BaseController{
	@RequestMapping(value = "list")
	public void list(HttpServletRequest request, HttpServletResponse response, Model model, CommonFee entity) {
		int pageNum = 1;
		String currentPage = request.getParameter("currentPage");
		StringBuffer respMsg = new StringBuffer();

		if (currentPage != null) {
			pageNum = Integer.valueOf(currentPage).intValue();
		}
		PaginationQuery pq = new PaginationQuery(facade, entity);
		pq.enableLike();
		pq.setCurrentPage(pageNum);
		List list = pq.list();
		respMsg.append("{");
		respMsg.append("\"status\":\"000000\",\"message\":\"ok\"");
		respMsg.append(",\"currentPage\":\"").append(currentPage).append("\"");
		respMsg.append(",\"totalPage\":\"").append(pq.getTotalPage()).append("\"");
		respMsg.append(",\"totalRecord\":\"").append(pq.getTotalRecord()).append("\"");
		respMsg.append(",\"list\":").append(JsonUtil.toJson(list));
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
	@RequestMapping(value = "load")
	public void load(HttpServletRequest request, HttpServletResponse response, Model model) {
		String merchantCode = request.getParameter("merchantCode");
		SimpleQuery sq = new SimpleQuery(facade, CommonFee.class);
		sq.eq("merchantCode", merchantCode);
		CommonFee entity = (CommonFee) sq.uniqueResult();
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
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, CommonFee entity) {
		facade.update(entity);

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
}
