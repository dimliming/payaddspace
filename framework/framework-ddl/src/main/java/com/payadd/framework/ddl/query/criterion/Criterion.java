package com.payadd.framework.ddl.query.criterion;

import java.util.List;

public interface Criterion extends java.io.Serializable{
	public String toSqlString(Criteria c);
	public List getParameters(Criteria c);
}
