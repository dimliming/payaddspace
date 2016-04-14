package com.payadd.polymer.auth.layer;

import com.payadd.framework.common.extension.Multiple;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.aut.Trade;

@Multiple
public interface AuthAgency {
	public AuthResult auth(DatabaseFacade facade,Trade trade);
	
	public AuthResult enquiry(DatabaseFacade facade,Trade trade);
}
