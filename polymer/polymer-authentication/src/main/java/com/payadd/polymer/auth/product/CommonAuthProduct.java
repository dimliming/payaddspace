package com.payadd.polymer.auth.product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.framework.ddl.query.SimpleQuery;
import com.payadd.polymer.auth.constant.IsWriteOff;
import com.payadd.polymer.auth.constant.MessageFields;
import com.payadd.polymer.auth.constant.NotifyStatus;
import com.payadd.polymer.auth.constant.SystemRespCode;
import com.payadd.polymer.auth.constant.TradeStatus;
import com.payadd.polymer.auth.layer.AuthAgency;
import com.payadd.polymer.auth.layer.AuthProduct;
import com.payadd.polymer.auth.protocol.AuthResultHelper;
import com.payadd.polymer.model.acc.Account;
import com.payadd.polymer.model.acc.AccountDetail;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.Trade;
import com.payadd.polymer.model.bdm.Merchant;

@ExtensionDescription(code = "common", name = "通用实名认证产品")
public class CommonAuthProduct implements AuthProduct {
	private AuthAgency agency;

	public AuthResult auth(DatabaseFacade facade, Trade trade) {
		// 1.检查是否有重复的商户订单号，如果有，封装错误信息返回
		AuthResult result = new AuthResult();
		SimpleQuery sq = new SimpleQuery(facade, Trade.class);
		sq.eq("merchantTradeNo", trade.getMerchantTradeNO());
		sq.eq("merchantCode", trade.getMerchantCode());
		if (sq.uniqueResult() != null) {
			result.setResultCode(SystemRespCode.MERCHANT_TRADE_NO_REPEAT);
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());
			return result;
		}

		// 2.生成trade_no
		// TODO:trade no generator

		// 3.初始化trade的状态为0-未提交渠道，product_code="common"
		trade.setStatus(TradeStatus.CHANNAL_UNCOMMIT);// TODO:constant
		trade.setProductCode("common");
		// 4.默认设置is_write_off=N，notify_status=1，resp_code=null，resp_msg=null，notify_status=0，fee=手续费
		trade.setIsWriteOff(IsWriteOff.NO);// TODO:
		trade.setNotifyStatus(NotifyStatus.UNFEEDBACK);
		trade.setRespCode(null);
		trade.setRespMsg(null);
		trade.setFee(BigDecimal.valueOf(0.2));
		// 5.判断商户状态，如果商户状态不正常，那么将status=1-交易终止，resp_code=120001，resp_msg=商户状态异常或商户不存在，保存trade到数据库中，返回
		SimpleQuery mercSq = new SimpleQuery(facade, Merchant.class);
		mercSq.eq("merchantCode", trade.getMerchantCode());
		Merchant merchant = (Merchant) mercSq.uniqueResult();
		if (!"1".equals(merchant.getStatus())) {
			// TODO:
			result.setResultCode(SystemRespCode.MERCHANT_ERR);
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());
			facade.insert(trade);
			return result;
		}
		// 6.如果校验通过，保存trade到数据库中
		facade.insert(trade);

		// 7.调用agency进行认证请求发送
		AuthResult agencyResult = agency.auth(facade, trade);

		// 9.将返回的状态码，更新status，将notify_status设置为1-已反馈
		trade.setNotifyStatus(1);
		if (AuthResultHelper.isSuccess(agencyResult)) {
			trade.setStatus(TradeStatus.AUTH_SUCCESS);
		} else {
			trade.setStatus(TradeStatus.AUTH_FAIL);
		}
		// 10.更新trade到数据库
		facade.update(trade);
		// 11.反馈结果给上一层
		return agencyResult;
	}

	public AuthResult enquiry(DatabaseFacade facade, String merchaneCode, String merchantTradeNo) {
		AuthResult result = new AuthResult();
		// 1.根据merchaneCode和merchantTradeNo查询trade
		List<String> parameter = new ArrayList<String>();
		parameter.add(merchaneCode);
		parameter.add(merchantTradeNo);
		SimpleQuery sq = new SimpleQuery(facade, Trade.class);
		sq.eq("merchantTradeNo", merchantTradeNo);
		sq.eq("merchantCode", merchaneCode);
		Trade trade = (Trade) sq.uniqueResult();
		// 2.如果trade==null，封装错误信息返回
		if (trade == null) {
			result.setResultCode(SystemRespCode.MERCHANT_TRADE_NULL);
			result.setResultDesc(AuthResultHelper.getDesc(result.getResultCode()));
			result.setReturnMsg("resp_code=" + result.getResultCode() + "&resp_msg=" + result.getResultDesc());
			return result;
		}
		// 3.如果trade的状态=2-已提交渠道，那么调用agency.enquiry获取最新的状态
		if (trade.getStatus() == 2) {
			agency.enquiry(facade, trade);
		}
		// 4.如果获取回来的状态有变化，更新到trade中

		// 5.获取trade的status，反馈给上一层
		return result;
	}

	public void writeOff(DatabaseFacade facade) {
		// 此函数将所有未核销的单据进行核销
		// 1.查找所有is_write_off=N and status=4的交易，
		SimpleQuery simpleQuery = new SimpleQuery(facade, Trade.class);
		simpleQuery.eq("isWriteOff", 'N');
		simpleQuery.eq("isWriteOff", 4);
		@SuppressWarnings("unchecked")
		List<Trade> list = simpleQuery.ListEntity();
		// 2.循环每一条交易
		SimpleQuery merchantQuery = new SimpleQuery(facade, Account.class);

		for (Trade trade : list) {
			// 3.检查商户余额是否足够，如果不够，发送邮件给管理员，退出循环
			merchantQuery.eq("accountNo", trade.getAccountNo());
			merchantQuery.eq("merchantCode", trade.getMerchantCode());
			Account account = (Account) merchantQuery.uniqueResult();
			if (-1 == account.getBalance().compareTo(trade.getFee())) {
				// 发送邮件
			} else {
				// 4.如果足够，商户余额减去交易费用，更新商户账户表，并记录账户收支明细记录
				// TODO:id generator
				AccountDetail accountDetail = new AccountDetail();
				accountDetail.setAccountNo(account.getAccountNo());
				accountDetail.setMerchantCode(account.getMerchantCode());
				accountDetail.setTradeTime(trade.getTradeTime());
				accountDetail.setAccountTime(new Timestamp(System.currentTimeMillis()));
				accountDetail.setTradeType(20);
				accountDetail.setTradeNo(trade.getTradeNo());
				accountDetail.setAmt(account.getBalance());
				account.setBalance(account.getBalance().subtract(trade.getFee()));

				// TODO:记录账户收支明细
				accountDetail.setBalance(account.getBalance());
				facade.update(account);
				facade.insert(accountDetail);
			}

		}

	}

}
