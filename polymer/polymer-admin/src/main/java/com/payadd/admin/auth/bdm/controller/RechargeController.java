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

import com.payadd.admin.auth.bdm.service.AccountDetailService;
import com.payadd.admin.auth.bdm.service.AccountService;
import com.payadd.admin.auth.bdm.service.RechargeService;
import com.payadd.framework.common.toolkit.IdGenerator;
import com.payadd.framework.common.toolkit.JsonUtil;
import com.payadd.framework.ddl.query.PaginationQuery;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.polymer.base.BaseController;
import com.payadd.polymer.model.acc.Account;
import com.payadd.polymer.model.acc.AccountDetail;
import com.payadd.polymer.model.acc.Recharge;
import com.payadd.polymer.model.bdm.Merchant;
import com.payadd.polymer.model.sys.User;

@Controller("rechargeController")
@RequestMapping("/recharge")
public class RechargeController extends BaseController {
	@Resource(name = "accountService")
	private AccountService accountService;
	@Resource(name = "rechargeService")
	private RechargeService rechargeService;
	@Resource(name = "accountDetailService")
	private AccountDetailService accountDetailService;

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
		SimpleQuery sq = new SimpleQuery(facade, Account.class);
		sq.eq("merchantCode", merchantCode);
		Account entity = (Account) sq.uniqueResult();
		if (entity == null) {
			entity = new Account();
			entity.setAccountNo(IdGenerator.nextLongSequence(Account.class).toString());
			entity.setMerchantCode(merchantCode);
			entity.setOpenTime(new Timestamp(System.currentTimeMillis()));
			entity.setStatus(0);
			entity.setBalance(BigDecimal.ZERO);
			entity.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
			accountService.insert(entity);
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
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, Account entity) {
		// 查找当前账户
		SimpleQuery sq = new SimpleQuery(facade, Account.class);
		sq.eq("accountNo", entity.getAccountNo());
		Account lastAccount = (Account) sq.uniqueResult();

		// 充值记录
		Recharge recharge = new Recharge();
		recharge.setRechargeNo(IdGenerator.nextLongSequence(AccountDetail.class).toString());
		recharge.setMerchantCode(entity.getMerchantCode());
		recharge.setChannelCode("shengda");
		recharge.setRechargeTime(new Timestamp(System.currentTimeMillis()));
		recharge.setAmt(entity.getBalance());

		rechargeService.insert(recharge);

		// 账户收支明细
		AccountDetail accountDetail = new AccountDetail();
		accountDetail.setId(IdGenerator.nextLongSequence(AccountDetail.class));
		accountDetail.setAccountNo(lastAccount.getAccountNo());
		accountDetail.setMerchantCode(lastAccount.getMerchantCode());

		accountDetail.setTradeTime(new Timestamp(System.currentTimeMillis()));
		accountDetail.setAccountTime(new Timestamp(System.currentTimeMillis()));
		accountDetail.setTradeType(10);
		accountDetail.setTradeNo(recharge.getRechargeNo());
		accountDetail.setAmt(lastAccount.getBalance());
		// 余额加上充值金额
		accountDetail.setBalance(lastAccount.getBalance().add(entity.getBalance()));
		accountDetailService.insert(accountDetail);

		
		entity.setBalance(accountDetail.getBalance());

		accountService.update(entity);
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
