package com.payadd.framework.ddl.query.criterion;

import java.util.ArrayList;
import java.util.List;

public class ColumnExpression implements Criterion {

	private final String columnName;
	private final Object value;
	private Object otherValue;
	//private boolean ignoreCase;
	private final String op;
	
	public ColumnExpression(String columnName,String op,Object value){
		this.columnName = columnName;
		this.op = op;
		this.value = value;
	}
	public ColumnExpression(String columnName,String op,Object value,Object otherValue){
		this.columnName = columnName;
		this.op = op;
		this.value = value;
		this.otherValue = otherValue;
	}
	public List getParameters(Criteria c) {
		if (value==null)return null;
		List list = new ArrayList();
		list.add(value);
		if (op!=null&&"between".equals(op.toLowerCase())){
			list.add(otherValue);
		}
		return list;
	}

	public String toSqlString(Criteria c) {
		StringBuffer sql = new StringBuffer();
		sql.append(columnName).append(" ").append(op);
		if (value==null)sql.append(" null ");
		else sql.append(" ? ");
		
		return sql.toString();
	}

}
