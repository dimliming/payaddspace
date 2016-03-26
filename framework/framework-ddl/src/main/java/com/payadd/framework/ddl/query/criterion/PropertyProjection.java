package com.payadd.framework.ddl.query.criterion;

public class PropertyProjection implements Projection {

	private final String propertyName;
	private String alias ;
	private boolean isGroup;
	private int priority;
	
	public PropertyProjection(String propertyName){
		this(propertyName,false,-1);
	}
	public PropertyProjection(String propertyName,boolean isGroup){
		this(propertyName,isGroup,-1);
	}
	public PropertyProjection(String propertyName,boolean isGroup,int priority){
		this.propertyName = propertyName;
		this.alias = null;
		this.isGroup = isGroup;
		this.priority = priority;
	}
	public PropertyProjection(String propertyName,String alias){
		this.propertyName = propertyName;
		this.alias = alias;
	}
	public PropertyProjection as(String alias){
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
			return c.getColumnName(propertyName);
		}else{
			return null;
		}
	}

	public String toSqlString(Criteria c) {
		String columnName = c.getColumnName(propertyName);
		StringBuffer sql = new StringBuffer();
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
	public String getPropertyName() {
		return propertyName;
	}
	public int getPriority() {
		return priority;
	}
}
