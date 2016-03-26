package com.payadd.polymer.foundation.layer;

import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.polymer.model.common.ProductContext;
import com.payadd.polymer.model.common.Result;

public interface Product {
	public Result forward(DatabaseFacade facade,String purpose,ProductContext<?> context);
	public Result backward(DatabaseFacade facade,String purpose,ProductContext<?> context);
}
