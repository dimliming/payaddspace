package com.payadd.polymer.base;

import javax.annotation.Resource;

import com.payadd.framework.ddl.DatabaseFacade;

public class BaseController {
	@Resource(name="facade")
	protected DatabaseFacade facade;
}
