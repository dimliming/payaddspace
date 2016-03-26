package com.payadd.framework.ddl.query.criterion;

public class Distinct implements Projection {
	private Projection projection;
	
	public Distinct(Projection projection){
		this.projection = projection;
	}
	
	public String[] getColumnAliases() {
		return projection.getColumnAliases();
	}

	public boolean isGrouped() {
		return projection.isGrouped();
	}

	public String toGroupSqlString(Criteria c) {
		return projection.toGroupSqlString(c);
	}

	public String toSqlString(Criteria c) {
		return "distinct "+projection.toSqlString(c);
	}
	public int getPriority() {
		return projection.getPriority();
	}
}
