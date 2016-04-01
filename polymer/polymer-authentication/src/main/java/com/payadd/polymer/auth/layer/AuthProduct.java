package com.payadd.polymer.auth.layer;

import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.model.aut.Trade;
import com.payadd.polymer.model.common.Result;

public interface AuthProduct {
	//认证
	public Result auth(DatabaseFacade facade,Trade trade);
	//认证查询
	public Result enquiry(DatabaseFacade facade,String merchaneCode,String merchantTradeNo);
	//核销
	public void writeOff(DatabaseFacade facade);
}
