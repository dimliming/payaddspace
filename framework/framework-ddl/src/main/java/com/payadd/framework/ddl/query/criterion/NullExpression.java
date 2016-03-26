package com.payadd.framework.ddl.query.criterion;

import java.util.List;

public class NullExpression implements Criterion {
	private final String propertyName;
	
	public NullExpression(String propertyName){
		this.propertyName = propertyName;
	}
	public List getParameters(Criteria c) {
		return null;
	}

	public String toSqlString(Criteria c) {
		String columnName = c.getColumnName(propertyName);
		StringBuffer sql = new StringBuffer();
		sql.append(columnName).append(" is null ");
		return sql.toString();
	}

}
