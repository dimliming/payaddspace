package com.payadd.polymer.auth.layer;

import com.payadd.framework.common.extension.Multiple;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.model.aut.AuthResult;
import com.payadd.polymer.model.common.RawMessage;

@Multiple
public interface AuthProtocol {
	public AuthResult auth(DatabaseFacade facade,RawMessage msg);
	
	public AuthResult enquiry(DatabaseFacade facade,RawMessage msg);
}
