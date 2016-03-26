package com.payadd.framework.ddl.query.criterion;

public class ColumnProjection implements Projection {

	private static final long serialVersionUID = 3375391696707426459L;
	private final String columnName;
	private String alias ;
	private boolean isGroup;
	private int priority;
	
	public ColumnProjection(String columnName){
		this(columnName,false,-1);
	}
	public ColumnProjection(String columnName,boolean isGroup){
		this(columnName,isGroup,-1);
	}
	public ColumnProjection(String columnName,boolean isGroup,int priority){
		this.columnName = columnName;
		this.alias = null;
		this.isGroup = isGroup;
		this.priority = priority;
	}
	public ColumnProjection(String columnName,String alias){
		this.columnName = columnName;
		this.alias = alias;
	}
	public ColumnProjection as(String alias){
		this.alias = alias;
		return this;
	}
	public String[] getColumnAliases() {
		if (this.alias==null)return null;
		else return new String[]{alias};
	}

	public boolean isGrouped() {
		return isGroup;
	}

	public String toGroupSqlString(Criteria c) {
		if (isGroup){
			return c.getPrefix()+columnName;
		}else{
			return null;
		}
	}

	public String toSqlString(Criteria c) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(c.getPrefix());
		sql.append(columnName);
		if (alias!=null)sql.append(" ").append(alias);
		return sql.toString();
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	public String getColumnName() {
		return columnName;
	}
	public int getPriority() {
		return priority;
	}
}
