package com.payadd.framework.ddl.query.criterion;

public class ExistsSubqueryExpression extends SubqueryExpression {
	
	public ExistsSubqueryExpression(String quantifier,Criteria c){
		super(null,quantifier,c);
	}
	protected String toLeftSqlString(Criteria criteria) {
		return "";
	}

}
