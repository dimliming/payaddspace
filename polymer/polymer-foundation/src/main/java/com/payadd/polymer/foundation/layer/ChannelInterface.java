package com.payadd.polymer.foundation.layer;

import com.payadd.framework.ddl.DatabaseFacade;

public interface ChannelInterface {
	public Object forward(DatabaseFacade facade,String purpose);
	public Object backward(DatabaseFacade facade,String purpose);
}
