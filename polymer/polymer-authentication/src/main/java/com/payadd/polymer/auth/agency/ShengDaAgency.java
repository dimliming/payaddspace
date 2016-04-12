package com.payadd.polymer.auth.agency;

import java.io.IOException;
import java.util.Properties;

import com.payadd.framework.common.extension.ExtensionDescription;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.auth.layer.AuthAgency;
import com.payadd.polymer.auth.layer.AuthDocking;
import com.payadd.polymer.model.aut.AuthDockingConfig;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.Trade;

@ExtensionDescription(code = "shengda", name = "盛大")
public class ShengDaAgency implements AuthAgency {
	private static final String CONFIG_LOCATION = "shengda-agency.properties";
	private static AuthDockingConfig config;

	private AuthDocking docking;

	private static void loadConfig(String type) {

		if (config != null)
			return;

		config = new AuthDockingConfig();
		Properties dockingProp = new Properties();

		try {
			dockingProp.load(ShengDaAgency.class.getClassLoader().getResource(CONFIG_LOCATION).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// TODO:
		if ("auth".equals(type)) {
			config.setAuthURL(dockingProp.getProperty("authURL"));
			config.setMd5Key(dockingProp.getProperty("sign_key"));
			config.setSubMerchantCode(dockingProp.getProperty("merchant_code"));
		} else if ("enquiry".equals(type)) {
			config.setEnquiryURL(dockingProp.getProperty("enquiryURL"));
			config.setMd5Key(dockingProp.getProperty("sign_key"));
			config.setSubMerchantCode(dockingProp.getProperty("merchant_code"));
		}

	}

	public AuthResult auth(DatabaseFacade facade, Trade trade) {
		// 1.查找入网参数，拼装AuthDockingConfig对象（从配置文件获取）
		loadConfig("auth");

		// 2.调用docking.auth
		AuthResult result = docking.auth(facade, trade, config);
		// 3.设置trade的agencyCode="shengda"
		trade.setAgencyCode("shengda");
		// 4.返回
		return result;
	}

	public AuthResult enquiry(DatabaseFacade facade, Trade trade) {
		// 1.查找入网参数，拼装AuthDockingConfig对象（从配置文件获取）
		loadConfig("enquiry");
		// 2.调用docking.enquiry
		AuthResult result = docking.enquiry(facade, trade, config);
		// 4.返回
		return result;
	}

}
