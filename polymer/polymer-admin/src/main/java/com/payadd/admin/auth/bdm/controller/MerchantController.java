package com.payadd.admin.auth.bdm.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.payadd.admin.auth.bdm.service.MerchantService;
import com.payadd.framework.common.toolkit.IdGenerator;
import com.payadd.framework.common.toolkit.JsonUtil;
import com.payadd.framework.ddl.query.PaginationQuery;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.polymer.base.BaseController;
import com.payadd.polymer.model.bdm.Merchant;
import com.payadd.polymer.model.sys.User;

@Controller("merchantController")
@RequestMapping("/merchant")
public class MerchantController extends BaseController {
	@Resource(name="merchantService")
	private MerchantService merchantService;
	
	@RequestMapping(value="list")
	public void list(HttpServletRequest request, HttpServletResponse response, Model model,Merchant merchant){
		System.out.println("merchant="+merchant.getMerchantCode());
		
		String currentPage = request.getParameter("currentPage");
		int pageNum = 1;
		if (currentPage!=null){
			pageNum = Integer.valueOf(currentPage).intValue();
		}
		PaginationQuery pq = new PaginationQuery(facade,merchant);
		pq.enableLike();
		pq.setCurrentPage(pageNum);
		List list = pq.list();
		
		StringBuffer respMsg = new StringBuffer();
		respMsg.append("{");
		respMsg.append("\"status\":\"000000\",\"message\":\"ok\"");
		respMsg.append(",\"currentPage\":\"").append(pq.getCurrentPage()).append("\"");
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
	@RequestMapping(value="load")
	public void load(HttpServletRequest request, HttpServletResponse response, Model model){
		String merchantCode = request.getParameter("merchantCode");
		SimpleQuery sq = new SimpleQuery(facade,Merchant.class);
		sq.eq("merchantCode", merchantCode);
		Merchant entity = (Merchant)sq.uniqueResult();
		
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
	@RequestMapping(value="save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model,Merchant entity){
		User user = getCurrentUser(request);
		
		if (entity.getMerchantCode()==null){
			entity.setMerchantCode(IdGenerator.nextLongSequence(Merchant.class).toString());
			entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			entity.setCreateUserId(user.getLoginName());
			entity.setLevel("00");
			merchantService.insert(entity);
		}else{
			entity.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			entity.setUpdateUserId(user.getLoginName());
			merchantService.update(entity);
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
	
	private User getCurrentUser(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute(LOGIN_USER);
		return user;
	}
}
