package com.payadd.polymer.auth.layer;

import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.model.aut.AuthDockingConfig;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.Trade;

public interface AuthDocking {
	public AuthResult auth(DatabaseFacade facade,Trade trade,AuthDockingConfig config );
	
	public AuthResult enquiry(DatabaseFacade facade,Trade trade,AuthDockingConfig config);
}
