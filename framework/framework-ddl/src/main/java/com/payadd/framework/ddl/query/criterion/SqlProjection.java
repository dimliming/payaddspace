package com.payadd.framework.ddl.query.criterion;

public class SqlProjection implements Projection {
	private final String sql ;
	
	public SqlProjection(String sql){
		this.sql = sql;
	}
	public String[] getColumnAliases() {
		return null;
	}

	public boolean isGrouped() {
		return false;
	}

	public String toGroupSqlString(Criteria c) {
		return null;
	}

	public String toSqlString(Criteria c) {
		return sql;
	}
	public int getPriority() {
		return -1;
	}
}
