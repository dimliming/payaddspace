package com.payadd.framework.ddl.mybatis;

import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

public class NativeSqlSource implements SqlSource {
	private String sql;
//	private List parameters;
//	private Object parameterObject;
	private List<ParameterMapping> parameterMappings;
	private Configuration configuration;
	
//	public NativeSqlSource(Configuration configuration,String sql,List parameters){
//		this.configuration = configuration;
//		this.sql = sql;
//		this.parameters = parameters;
//		
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		for (int i=0;i<parameters.size();i++)paramMap.put("prop"+i, parameters.get(i));
//		parameterObject = paramMap;
//		
//		parameterMappings = new ArrayList<ParameterMapping>();
//		for (int i=0;i<parameters.size();i++){
//			Object value = parameters.get(i);
//			Class javaType = Object.class;
//			if (value!=null)javaType = value.getClass();
//			ParameterMapping.Builder pmBuilder = new ParameterMapping.Builder(configuration, "prop"+i, javaType);
//			parameterMappings.add(pmBuilder.build());
//		}
//	}
	public NativeSqlSource(Configuration configuration,String sql,ParameterMap parameterMap){
		this.configuration = configuration;
		this.sql = sql;
		this.parameterMappings = parameterMap.getParameterMappings();
	}
	
	public BoundSql getBoundSql(Object parameterObject) {
		BoundSql bs = new BoundSql(configuration,sql,parameterMappings,parameterObject);
		return bs;
	}

}
