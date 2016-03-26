package com.payadd.framework.ddl.query.criterion;

public class CountProjection extends AggregateProjection {
	private boolean distinct = false;
	
	protected CountProjection(String propertyName){
		super("count",propertyName);
	}
	public String toSqlString(Criteria c) {
		String columnName = c.getColumnName(propertyName);
		StringBuffer buf = new StringBuffer();
		buf.append("count(");
		if (distinct) buf.append("distinct ");
		buf.append(columnName).append(") ");
		
		if (alias!=null)buf.append(alias);
		return buf.toString();
	}

	public CountProjection setDistinct(){
		this.distinct = true;
		return this;
	}
}
