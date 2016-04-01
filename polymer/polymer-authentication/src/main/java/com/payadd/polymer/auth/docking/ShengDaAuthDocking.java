package com.payadd.polymer.auth.docking;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.auth.layer.AuthDocking;
import com.payadd.polymer.model.aut.AuthDockingConfig;
import com.payadd.polymer.model.aut.Trade;
import com.payadd.polymer.model.common.Result;

@ExtensionDescription(code="shengda",name="盛大")
public class ShengDaAuthDocking implements AuthDocking {

	public Result auth(DatabaseFacade facade, Trade trade, AuthDockingConfig config) {
		//1.组装认证报文
		//2.签名
		//3.发送报文
		//4.获取返回报文，解析，验签，验签不通过，组装错误信息返回
		//5.记录ChannelMessage日志信息
		//6.获取认证结果
		//7.将结果封装到Result，返回
		return null;
	}

	public Result enquiry(DatabaseFacade facade, Trade trade, AuthDockingConfig config) {
		//1.组装查询报文
		//2.签名
		//3.发送报文
		//4.获取返回报文，解析，验签，验签不通过，组装错误信息返回
		//5.记录ChannelMessage日志信息
		//6.获取认证结果
		//7.将结果封装到Result，返回
		return null;
	}

}