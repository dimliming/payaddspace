package com.payadd.framework.ddl.query.criterion;

public class ConstProjection implements Projection {
	public final String constStr;
	public ConstProjection(String constStr){
		this.constStr = constStr;
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
		return constStr;
	}
	public int getPriority() {
		return -1;
	}
}
