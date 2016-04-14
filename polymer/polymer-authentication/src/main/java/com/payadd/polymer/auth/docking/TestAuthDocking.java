package com.payadd.polymer.auth.docking;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.auth.layer.AuthDocking;
import com.payadd.polymer.model.aut.AuthDockingConfig;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.Trade;

@ExtensionDescription(code = "test", name = "测试认证接口")
public class TestAuthDocking implements AuthDocking {

	@Override
	public AuthResult auth(DatabaseFacade facade, Trade trade, AuthDockingConfig config) {
		AuthResult result = new AuthResult();
		trade.setChannelTradeNo("10000000001");
		trade.setRespCode("000000");
		trade.setRespMsg("成功");
		
		result.setResultCode(trade.getRespCode());
		return result;
	}

	@Override
	public AuthResult enquiry(DatabaseFacade facade, Trade trade, AuthDockingConfig config) {
		AuthResult result = new AuthResult();
		
		result.setResultCode("000000");
		
		return result;
	}

}
