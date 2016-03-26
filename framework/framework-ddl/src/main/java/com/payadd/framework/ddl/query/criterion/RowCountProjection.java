package com.payadd.framework.ddl.query.criterion;


public class RowCountProjection extends AggregateProjection{

	public RowCountProjection(){
		super(null,null);
		
	}
	public String toSqlString(Criteria criteria){
		StringBuffer sql = new StringBuffer();
		sql.append("count(*) ");
		
		if (alias!=null)sql.append(alias);
		
		return sql.toString();
	}

}
