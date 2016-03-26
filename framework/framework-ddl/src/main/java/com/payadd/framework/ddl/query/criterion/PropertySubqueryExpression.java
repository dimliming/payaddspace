package com.payadd.framework.ddl.query.criterion;

public class PropertySubqueryExpression extends SubqueryExpression {
	public final String propertyName;
	
	public PropertySubqueryExpression(String propertyName,String op,String quantifier,Criteria c){
		super(op,quantifier,c);
		this.propertyName = propertyName;
	}

	protected String toLeftSqlString(Criteria criteria) {
		String columnName = criteria.getColumnName(propertyName);
		return columnName;
	}

}
