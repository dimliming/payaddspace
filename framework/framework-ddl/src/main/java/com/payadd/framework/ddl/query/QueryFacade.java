package com.payadd.framework.ddl.query;

import java.util.List;

public interface QueryFacade extends java.io.Serializable{
	public List list();
	public Object uniqueResult();
}
