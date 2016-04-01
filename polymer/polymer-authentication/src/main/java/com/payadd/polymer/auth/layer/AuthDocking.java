package com.payadd.polymer.auth.layer;

import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.model.aut.AuthDockingConfig;
import com.payadd.polymer.model.aut.Trade;
import com.payadd.polymer.model.common.Result;

public interface AuthDocking {
	public Result auth(DatabaseFacade facade,Trade trade,AuthDockingConfig config );
	
	public Result enquiry(DatabaseFacade facade,Trade trade,AuthDockingConfig config);
}
