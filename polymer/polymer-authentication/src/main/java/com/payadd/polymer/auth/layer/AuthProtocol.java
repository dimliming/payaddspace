package com.payadd.polymer.auth.layer;

import com.payadd.framework.common.extension.Multiple;
import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.model.common.RawMessage;
import com.payadd.polymer.model.common.Result;

@Multiple
public interface AuthProtocol {
	public Result auth(DatabaseFacade facade,RawMessage msg);
	
	public Result enquiry(DatabaseFacade facade,RawMessage msg);
}
