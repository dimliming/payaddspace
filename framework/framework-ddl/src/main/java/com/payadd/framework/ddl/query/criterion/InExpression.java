package com.payadd.framework.ddl.query.criterion;

import java.util.List;

public class InExpression implements Criterion {
	private String propertyName;
	private String values;
	
	public InExpression(String propertyName,String values){
		this.propertyName = propertyName;
		this.values = values;
	}
	public List getParameters(Criteria c) {
		// TODO Auto-generated method stub
		return null;
	}

	public String toSqlString(Criteria c) {
		String columnName = c.getColumnName(propertyName);
		
		StringBuffer sql = new StringBuffer();
		sql.append(columnName).append(" in (");
		if (values!=null)sql.append(values);
		sql.append(" ) ");
		
		
		return sql.toString();
	}

}
