package com.payadd.admin.auth.bdm.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.payadd.admin.auth.bdm.service.AccountService;
import com.payadd.admin.auth.bdm.service.MerchantService;
import com.payadd.admin.auth.bdm.service.RechargeService;
import com.payadd.framework.common.toolkit.IdGenerator;
import com.payadd.framework.common.toolkit.JsonUtil;
import com.payadd.framework.ddl.query.PaginationQuery;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.merchant.contant.MerchantContant;
import com.payadd.polymer.base.BaseController;
import com.payadd.polymer.model.acc.Account;
import com.payadd.polymer.model.bdm.Merchant;
import com.payadd.polymer.model.bdm.MerchantUser;
import com.payadd.polymer.model.sys.User;

@Controller("merchantController")
@RequestMapping("/merchant")
public class MerchantController extends BaseController {
	@Resource(name = "merchantService")
	private MerchantService merchantService;
	@Resource(name = "accountService")
	private AccountService accountService;

	@RequestMapping(value = "list")
	public void list(HttpServletRequest request, HttpServletResponse response, Model model, Merchant merchant) {
		System.out.println("merchant=" + merchant.getMerchantCode());
		System.out.println(merchant);
		String currentPage = request.getParameter("currentPage");
		int pageNum = 1;
		if (currentPage != null) {
			pageNum = Integer.valueOf(currentPage).intValue();
		}
		PaginationQuery pq = new PaginationQuery(facade, merchant);
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

	@RequestMapping(value = "load")
	public void load(HttpServletRequest request, HttpServletResponse response, Model model) {
		String merchantCode = request.getParameter("merchantCode");
		SimpleQuery sq = new SimpleQuery(facade, Merchant.class);
		sq.eq("merchantCode", merchantCode);
		Merchant entity = (Merchant) sq.uniqueResult();
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
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, Merchant entity) {
		User user = getCurrentUser(request);

		if (entity.getMerchantCode() == null) {
			entity.setMerchantCode(IdGenerator.nextLongSequence(Merchant.class).toString());
			entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			entity.setCreateUserId(user.getLoginName());
			entity.setLevel("00");
			//为新增商户添加后台用户
			MerchantUser merchantUser = new MerchantUser();
			merchantUser.setId(IdGenerator.nextLongSequence(Account.class));
			merchantUser.setMerchantCode(entity.getMerchantCode());
			merchantUser.setUserName(entity.getFullName());
			merchantUser.setPhone(entity.getCompanyPhone());
			merchantUser.setEmail(entity.getCompanyEmail());
			merchantUser.setLoginName(entity.getMerchantCode());
			merchantUser.setPassword(MerchantContant.DEFAULT_PASSWORD);
			merchantUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
			merchantUser.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			facade.insert(merchantUser);
			
			// 为新增用户开账户
			Account account = new Account();
			account.setAccountNo(IdGenerator.nextLongSequence(Account.class).toString());
			account.setMerchantCode(entity.getMerchantCode());
			account.setOpenTime(new Timestamp(System.currentTimeMillis()));
			account.setStatus(0);
			account.setBalance(BigDecimal.ZERO);
			account.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
			accountService.insert(account);
			merchantService.insert(entity);
		} else {
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

	private User getCurrentUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGIN_USER);
		return user;
	}
}
