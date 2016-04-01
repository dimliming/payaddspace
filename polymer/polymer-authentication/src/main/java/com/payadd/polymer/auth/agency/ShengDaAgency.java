package com.payadd.polymer.auth.agency;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.auth.layer.AuthAgency;
import com.payadd.polymer.auth.layer.AuthDocking;
import com.payadd.polymer.model.aut.Trade;
import com.payadd.polymer.model.common.Result;

@ExtensionDescription(code="shengda",name="盛大")
public class ShengDaAgency implements AuthAgency {
	private AuthDocking docking;
	
	public Result auth(DatabaseFacade facade, Trade trade) {
		//1.查找入网参数，拼装AuthDockingConfig对象（从配置文件获取）
		//2.调用docking.auth
		//3.设置trade的agencyCode="shengda"
		//4.返回
		return null;
	}

	public Result enquiry(DatabaseFacade facade, Trade trade) {
		//1.查找入网参数，拼装AuthDockingConfig对象（从配置文件获取）
		//2.调用docking.enquiry
		//4.返回
		return null;
	}

}
