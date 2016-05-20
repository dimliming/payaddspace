package com.payadd.polymer.auth.agency;

import java.io.IOException;
import java.util.Properties;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.common.extension.Router;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.auth.layer.AuthAgency;
import com.payadd.polymer.auth.layer.AuthDocking;
import com.payadd.polymer.model.aut.AuthDockingConfig;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.Trade;

@ExtensionDescription(code = "shengda", name = "盛大")
public class ShengDaAgency implements AuthAgency {
	public static final String CONFIG_LOCATION = "docking-config/shengda-agency.properties";
	public static final String CONFIG_LOCATION_TEST = "docking-config/test-agency.properties";
	private static AuthDockingConfig config;

	@Router("authDockingRouter")
	private AuthDocking docking;

	public static void loadConfig(String isTest) {
		if("N".equals(isTest)){
			getConfig(CONFIG_LOCATION);
		}else{
			getConfig(CONFIG_LOCATION_TEST);
		}
		

	}

	public AuthResult auth(DatabaseFacade facade, Trade trade) {
		// 1.查找入网参数，拼装AuthDockingConfig对象（从配置文件获取）
		loadConfig(trade.getIsTest());
		
		// 2.设置trade的agencyCode="shengda"
		trade.setAgencyCode("shengda");
		// 3.调用docking.auth
		AuthResult result = docking.auth(facade, trade, config);
		
		// 4.返回
		return result;
	}

	public AuthResult enquiry(DatabaseFacade facade, Trade trade) {
		// 1.查找入网参数，拼装AuthDockingConfig对象（从配置文件获取）
		loadConfig(trade.getIsTest());
		
		trade.setAgencyCode("shengda");
		// 2.调用docking.enquiry
		AuthResult result = docking.enquiry(facade, trade, config);
		// 4.返回
		return result;
	}
	
	public static void getConfig(String configLocation){
		

		config = new AuthDockingConfig();
		Properties dockingProp = new Properties();

		try {
			dockingProp.load(ShengDaAgency.class.getClassLoader().getResource(configLocation).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// TODO:
		config.setAuthURL(dockingProp.getProperty("authURL"));
		config.setEnquiryURL(dockingProp.getProperty("enquiryURL"));
		config.setMd5Key(dockingProp.getProperty("sign_key"));
		config.setSubMerchantCode(dockingProp.getProperty("merchant_code"));
	}

}
