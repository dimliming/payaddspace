package com.payadd.framework.ddl.query.criterion;

import java.util.List;

public class SqlExpression implements Criterion {
	private final String sql;
	private final List parameters;
	
	public SqlExpression(String sql,List parameters){
		this.sql = sql;
		this.parameters = parameters;
	}
	public List getParameters(Criteria c) {
		return parameters;
	}

	public String toSqlString(Criteria c) {
		return "("+sql+")";
	}

}
