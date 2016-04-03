package com.payadd.polymer.auth.layer;

import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.Trade;

public interface AuthProduct {
	//认证
	public AuthResult auth(DatabaseFacade facade,Trade trade);
	//认证查询
	public AuthResult enquiry(DatabaseFacade facade,String merchaneCode,String merchantTradeNo);
	//核销
	public void writeOff(DatabaseFacade facade);
}
