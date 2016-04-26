package com.payadd.polymer.base;

import javax.annotation.Resource;

import com.payadd.framework.ddl.DatabaseFacade;

public class BaseController {
	public static final String 	LOGIN_USER = "login.user";
	
	@Resource(name="facade")
	protected DatabaseFacade facade;
}
