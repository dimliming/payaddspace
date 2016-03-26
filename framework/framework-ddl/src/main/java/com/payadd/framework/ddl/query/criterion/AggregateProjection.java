package com.payadd.framework.ddl.query.criterion;

public class AggregateProjection implements Projection {
	protected final String propertyName;
	private final String aggregate;
	protected String alias;
	
	protected AggregateProjection(String aggregate,String propertyName){
		this.propertyName = propertyName;
		this.aggregate = aggregate;
	}
	public AggregateProjection as(String alias){
		this.alias = alias;
		return this;
	}
	public String[] getColumnAliases() {
		if (alias==null)
			return null;
		else
			return new String[]{alias};
	}

	public boolean isGrouped() {
		return false;
	}

	public String toGroupSqlString(Criteria c) {
		return null;
	}

	public String toSqlString(Criteria c) {
		String columnName = c.getColumnName(propertyName);
		StringBuffer sql = new StringBuffer();
		sql.append(aggregate).append("(").append(columnName).append(")");
		if (alias!=null)sql.append(" ").append(alias);
		return sql.toString();
	}
	public int getPriority() {
		return -1;
	}

}
