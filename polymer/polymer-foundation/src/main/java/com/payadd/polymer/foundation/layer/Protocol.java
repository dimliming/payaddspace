package com.payadd.polymer.foundation.layer;

import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.model.common.RawMessage;
import com.payadd.polymer.model.common.Result;

public interface Protocol {
	public Result forward(DatabaseFacade facade,String purpose,RawMessage msg);
	public Result backward(DatabaseFacade facade,String purpose,RawMessage msg);
	
}
