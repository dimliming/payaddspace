package com.payadd.framework.ddl.query.criterion;

import java.util.ArrayList;
import java.util.List;

public class BetweenExpression implements Criterion {
	private final String propertyName;
	private final Object lower;
	private final Object upper;
	
	public BetweenExpression(String propertyName,Object lower,Object upper){
		this.propertyName = propertyName;
		this.lower = lower;
		this.upper = upper;
	}
	public List getParameters(Criteria c) {
		List list = new ArrayList();
		list.add(lower);
		list.add(upper);
		return list;
	}

	public String toSqlString(Criteria c) {
		String columnName = c.getColumnName(propertyName);
		StringBuffer sql = new StringBuffer();
		sql.append(columnName).append(" between ? and ? ");
		return sql.toString();
	}

}
