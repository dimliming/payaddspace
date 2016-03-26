package com.payadd.framework.ddl.query.criterion;

public interface CriteriaContext {
	public String getColumnName(String propertyName);
	public String getPropertyName(String columnName);
	public String[] allProperties();
	public String[] allColumns();
	public void set(Object obj,String property,Object value);
	public Object get(Object obj,String property);
	public Object getByColumn(Object obj,String column);
	public Class getReturnType();
	public String getTable();
	public String getSecurityId();
}
