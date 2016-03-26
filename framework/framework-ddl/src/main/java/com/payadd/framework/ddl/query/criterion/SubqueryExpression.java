package com.payadd.framework.ddl.query.criterion;

import java.util.List;

public abstract class SubqueryExpression implements Criterion {
	private final Criteria criteria;
	private final String quantifier;
	private final String op;
	
	public SubqueryExpression(String op,String quantifier,Criteria criteria){
		this.op = op;
		this.quantifier = quantifier;
		this.criteria = criteria;
	}
	public List getParameters(Criteria c) {
		return criteria.getParameters();
	}

	protected abstract String toLeftSqlString(Criteria criteria);

	public String toSqlString(Criteria c) {
		final StringBuffer buf = new StringBuffer().append(toLeftSqlString(
				c));
		if (op != null)
			buf.append(' ').append(op).append(' ');
		if (quantifier != null)
			buf.append(quantifier).append(' ');
		criteria.setFacade(c.getFacade());
		return buf.append('(').append(criteria.toSqlString()).append(')').toString();
	}

}
