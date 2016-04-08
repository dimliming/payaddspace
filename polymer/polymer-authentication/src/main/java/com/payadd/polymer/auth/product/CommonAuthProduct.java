package com.payadd.polymer.auth.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.auth.layer.AuthAgency;
import com.payadd.polymer.auth.layer.AuthProduct;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.Trade;

@ExtensionDescription(code = "common", name = "通用实名认证产品")
public class CommonAuthProduct implements AuthProduct {
	private AuthAgency agency;

	public AuthResult auth(DatabaseFacade facade, Trade trade) {
		// 1.检查是否有重复的商户订单号，如果有，封装错误信息返回
		AuthResult result = new AuthResult();
		boolean isRepeat = facade.equals("merchantTradeNO");
		facade.commit();
		if (isRepeat) {
			result.setReturnMsg("resp_code=E9&resp_msg=商户订单号重复");
			return result;
		}

		// 2.生成trade_no

		// 3.初始化trade的状态为0-未提交渠道，product_code="common"
		trade.setStatus(0);
		trade.setProductCode("common");
		// 4.默认设置is_write_off=N，notify_status=1，resp_code=null，resp_msg=null，notify_status=0，fee=手续费
		trade.setIsWriteOff('N');
		trade.setNotifyStatus(1);
		trade.setRespCode(null);
		trade.setRespMsg(null);
		trade.setNotifyStatus(0);
		trade.setFee(BigDecimal.valueOf(0.2));
		// 5.判断商户状态，如果商户状态不正常，那么将status=1-交易终止，resp_code=E1001，resp_msg=商户状态不正常，保存trade到数据库中，返回

		// 6.如果校验通过，保存trade到数据库中

		// 7.调用agency进行认证请求发送
		AuthResult agencyResult = agency.auth(facade, trade);
		// 8.根据返回的结果，更新channel_trade_no、channel_code、resp_code、resp_msg、

		// 9.将返回的状态码，更新status，将notify_status设置为1-已反馈

		// 10.更新trade到数据库
		// 11.反馈结果给上一层
		return null;
	}

	public AuthResult enquiry(DatabaseFacade facade, String merchaneCode, String merchantTradeNo) {
		AuthResult authResult = new AuthResult();
		// 1.根据merchaneCode和merchantTradeNo查询trade
		List<String> parameter = new ArrayList<String>();
		parameter.add(merchaneCode);
		parameter.add(merchantTradeNo);
		Trade trade = (Trade) facade.queryOne("select * from aut_trade where merchant_code=? and merchant_trade_no=?",
				parameter);
		facade.commit();
		// 2.如果trade==null，封装错误信息返回
		if (trade == null) {
			authResult.setReturnMsg("");
			return authResult;
		}
		// 3.如果trade的状态=2-已提交渠道，那么调用agency.enquiry获取最新的状态
		if (trade.getStatus() == 2) {
			agency.enquiry(facade, trade);
		}
		// 4.如果获取回来的状态有变化，更新到trade中

		// 5.获取trade的status，反馈给上一层
		return authResult;
	}

	public void writeOff(DatabaseFacade facade) {
		// 此函数将所有未核销的单据进行核销
		// 1.查找所有is_write_off=N and status=4的交易，
		List<String> parameter = new ArrayList<String>();
		parameter.add("N");
		parameter.add("4");
		List<Trade> tradeList = facade.query("select * from aut_trade where is_write_off=? and status=?", parameter);
		// 2.循环每一条交易
		for (Trade trade : tradeList) {
			// 3.检查商户余额是否足够，如果不够，发送邮件给管理员，退出循环

			// 4.如果足够，商户余额减去交易费用，更新商户账户表，并记录账户收支明细记录
		}

	}

}
