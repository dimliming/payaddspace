package com.payadd.framework.ddl.query.criterion;

import java.util.List;

public class PropertyExpression implements Criterion {
	private final String propertyName;
	private final String otherPropertyName;
	private final String op;
	private final PropertyProjection property;
	private final PropertyProjection otherProperty;
	
	public PropertyExpression(String propertyName,String op,String otherPropertyName){
		this.propertyName = propertyName;
		this.op = op;
		this.otherPropertyName = otherPropertyName;
		this.property = null;
		this.otherProperty = null;
	}
	public PropertyExpression(PropertyProjection property,String op,PropertyProjection otherProperty){
		this.propertyName = null;
		this.op = op;
		this.otherPropertyName = null;
		this.property = property;
		this.otherProperty = otherProperty;
	}
	
	public List getParameters(Criteria c) {
		return null;
	}

	public String toSqlString(Criteria c) {
		StringBuffer sql = new StringBuffer();
		if (propertyName!=null){
			String columnName = c.getColumnName(propertyName);
			String otherColumnName = c.getColumnName(otherPropertyName);
			sql.append(columnName).append(op).append(otherColumnName);
		}else{
			sql.append(property.toSqlString(c)).append(op).append(otherProperty.toSqlString(c));
		}
		return sql.toString();
	}

}
