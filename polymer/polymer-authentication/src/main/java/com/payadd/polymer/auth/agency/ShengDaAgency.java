package com.payadd.polymer.auth.agency;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.auth.layer.AuthAgency;
import com.payadd.polymer.auth.layer.AuthDocking;
import com.payadd.polymer.model.aut.AuthDockingConfig;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.Trade;

@ExtensionDescription(code="shengda",name="盛大")
public class ShengDaAgency implements AuthAgency {
	private AuthDocking docking;
	
	public AuthResult auth(DatabaseFacade facade, Trade trade) {
		//1.查找入网参数，拼装AuthDockingConfig对象（从配置文件获取）
		AuthDockingConfig config = new AuthDockingConfig();
		//2.调用docking.auth
		docking.auth(facade, trade, config);
		//3.设置trade的agencyCode="shengda"
		trade.setAgencyCode("shengda");
		//4.返回
		return null;
	}

	public AuthResult enquiry(DatabaseFacade facade, Trade trade) {
		//1.查找入网参数，拼装AuthDockingConfig对象（从配置文件获取）
		//2.调用docking.enquiry
		//4.返回
		return null;
	}

}
