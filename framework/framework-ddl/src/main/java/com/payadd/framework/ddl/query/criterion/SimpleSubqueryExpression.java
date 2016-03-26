package com.payadd.framework.ddl.query.criterion;

import java.util.ArrayList;
import java.util.List;


public class SimpleSubqueryExpression extends SubqueryExpression {
	private Object value;
	protected SimpleSubqueryExpression(Object value, String op, String quantifier, Criteria c) {
		super(op, quantifier, c);
		this.value = value;
	}
	protected String toLeftSqlString(Criteria criteria) {
		return " ? ";
	}
	public List getParameters(Criteria c){
		List list = super.getParameters(c);
		List newList = new ArrayList(list.size()+1);
		newList.add(value);
		newList.addAll(list);
		return newList;
	}

}
