package com.payadd.polymer.auth.docking;

import com.payadd.framework.common.extension.Route;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.auth.layer.AuthDocking;
import com.payadd.polymer.model.aut.AuthDockingConfig;
import com.payadd.polymer.model.aut.Trade;

@Route(code="authDockingRouter",name="认证接口通道路由器",target=AuthDocking.class)
public class AuthDockingRouter  {

	public String auth(DatabaseFacade facade, Trade trade, AuthDockingConfig config) {
		if (trade.getIsTest()!=null&&"Y".equals(trade.getIsTest())){
			return "test";
		}else{
			return "shengda";
		}
	}

	public String enquiry(DatabaseFacade facade, Trade trade, AuthDockingConfig config) {
		if (trade.getIsTest()!=null&&"".equals(trade.getIsTest())){
			return "test";
		}else{
			return "shengda";
		}
	}

}
