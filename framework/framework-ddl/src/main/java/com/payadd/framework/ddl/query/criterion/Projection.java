package com.payadd.framework.ddl.query.criterion;

import java.io.Serializable;

public interface Projection extends Priority,Serializable{
	public String toSqlString(Criteria c);

	public String toGroupSqlString(Criteria c) ;

	public String[] getColumnAliases();

	public boolean isGrouped();
}
