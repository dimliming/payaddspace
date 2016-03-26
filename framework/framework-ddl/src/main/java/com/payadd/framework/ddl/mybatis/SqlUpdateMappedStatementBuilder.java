package com.payadd.framework.ddl.mybatis;

import java.util.List;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;

public class SqlUpdateMappedStatementBuilder extends
		QueryMappedStatementBuilder {
	public SqlUpdateMappedStatementBuilder(Configuration configuration,String sql,List parameters){
		super(configuration,sql,parameters,-1,-1);
	}
	
//	public ResultMap getResultMap(String entityName){
//		return null;
//	}
}
