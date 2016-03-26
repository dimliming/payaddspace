package com.payadd.framework.ddl.query.criterion;

import java.util.ArrayList;
import java.util.List;

public class SimpleExpression implements Criterion {
	private final String propertyName;
	private final Object value;
	//private boolean ignoreCase;
	private final String op;
	
	public SimpleExpression(String propertyName,String op,Object value){
		this.propertyName = propertyName;
		this.op = op;
		this.value = value;
	}
	public List getParameters(Criteria c) {
		if (value==null)return null;
		List list = new ArrayList();
		if (op != null && op.trim().equalsIgnoreCase("like")) list.add("%"+value+"%");
		else list.add(value);
		return list;
	}

	public String toSqlString(Criteria c) {
		String columnName = c.getColumnName(propertyName);
		StringBuffer sql = new StringBuffer();
		sql.append(columnName).append(op);
		if (value==null)sql.append("null ");
		else sql.append("? ");
		
		return sql.toString();
	}

}
