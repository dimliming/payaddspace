package com.payadd.framework.ddl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import com.payadd.framework.common.exception.SystemException;

public class QueryMappedStatementBuilder extends MappedStatementBuilder {
	private static final List EMPTY_PARAMETER = new ArrayList();
	private String sql;
	private ParameterMap parameterMap;
	private Object parameterObject;
	private ResultMap resultMap;
	private EntityMetadata metadata;
	private Class type;
	
	public QueryMappedStatementBuilder(Configuration configuration,String entityName,String sql,List parameters, int skip, int max){
		this.configuration = configuration;
		this.sql = wrapSql(sql,skip,max);
		this.parameterMap = toParameterMap(parameters);
		this.parameterObject = toParameterObject(parameters);
		this.type = getType(entityName);
		this.resultMap = getResultMap(entityName);
	}
	public QueryMappedStatementBuilder(Configuration configuration,Class type,String sql,List parameters, int skip, int max){
		this.configuration = configuration;
		this.sql = wrapSql(sql,skip,max);
		this.parameterMap = toParameterMap(parameters);
		this.parameterObject = toParameterObject(parameters);
		this.type = type;
		this.resultMap = getResultMap(type.getName());
	}
	public QueryMappedStatementBuilder(Configuration configuration,String sql,List parameters, int skip, int max){
		this.configuration = configuration;
		this.sql = wrapSql(sql,skip,max);
		this.parameterMap = toParameterMap(parameters);
		this.parameterObject = toParameterObject(parameters);
		this.type = HashMap.class;
		this.resultMap = getResultMap(null);
	}

	public QueryMappedStatementBuilder(Configuration configuration,String entityName,Object id){
		this.configuration = configuration;
		this.type = getType(entityName);
		this.sql = getIdQuerySql(entityName);
		this.parameterObject = id;
		this.resultMap = getResultMap(entityName);
		this.parameterMap = getParameterMap(entityName);
	}
	public QueryMappedStatementBuilder(Configuration configuration,Class type , Object id){
		this.configuration = configuration;
		this.type = type;
		String entityName = type.getName();
		this.sql = getIdQuerySql(entityName);
		this.parameterObject = id;
		this.resultMap = getResultMap(entityName);
		this.parameterMap = getParameterMap(entityName);
	}
	private Object toParameterObject(List parameters){
		if (parameters==null)parameters = EMPTY_PARAMETER;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		for (int i=0;i<parameters.size();i++)
			paramMap.put("prop"+i, parameters.get(i));
		return paramMap;
	}
	private ParameterMap toParameterMap(List parameters){
		if (parameters==null)parameters = EMPTY_PARAMETER;
		List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
		for (int i=0;i<parameters.size();i++){
			Object value = parameters.get(i);
			Class javaType = Object.class;
			if (value!=null)javaType = value.getClass();
			ParameterMapping.Builder pmBuilder = new ParameterMapping.Builder(configuration, "prop"+i, javaType);
			parameterMappings.add(pmBuilder.build());
		}
		ParameterMap.Builder inlineParameterMapBuilder = new ParameterMap.Builder(
				configuration, "parameter_map_id",
				Map.class, parameterMappings);
		return inlineParameterMapBuilder.build();
	}
	private String wrapSql(String sql,int skip,int max){
		if (skip==-1||max==-1)return sql;
		StringBuffer paginationSql = new StringBuffer();
		paginationSql.append("select * from (").append(sql).append(") a limit ").append(skip).append(" , ").append(max);
		System.out.println("pagination sql="+paginationSql.toString());
		return paginationSql.toString();
	}
	private String getIdQuerySql(String entityName){
		EntityMetadata em = getEntityMetadata(entityName);
		StringBuffer idSelectSql = new StringBuffer("select * from ").append(em.getTable());
		idSelectSql.append(" where ");
		ResultMap rm =  getResultMap(entityName);
		List<ResultMapping> idList = rm.getIdResultMappings();
		for (int i=0;i<idList.size();i++){
			ResultMapping mapping = idList.get(i);
			if(i>0) idSelectSql.append(" and ");
			idSelectSql.append(mapping.getColumn()).append(" = ?");
		}
		return idSelectSql.toString();
	}
	public EntityMetadata getEntityMetadata(String entityName){
		if (metadata!=null)return metadata;
		//String entityName = getEntityName();
		metadata = ((MyConfiguration)this.configuration).getEntityMap().get(entityName);
		return metadata;
	}
	public Class getType(String entityName){
		EntityMetadata em = getEntityMetadata(entityName);
		try {
			Class type = Class.forName(em.getClassName());
			return type;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
	}
	public ParameterMap getParameterMap(String entityName){
		if (this.parameterMap!=null)return this.parameterMap;
		
		ResultMap rm =  getResultMap(entityName);
		List<ResultMapping> idList = rm.getIdResultMappings();
		List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
		for (int i=0;i<idList.size();i++){
			ResultMapping mapping = idList.get(i);
			Class javaType = Object.class;
			ParameterMapping.Builder pmBuilder = new ParameterMapping.Builder(configuration, mapping.getProperty(), javaType);
			parameterMappings.add(pmBuilder.build());
		}
		ParameterMap.Builder inlineParameterMapBuilder = new ParameterMap.Builder(
				configuration, "parameter_map_id",
				this.type, parameterMappings);
		this.parameterMap = inlineParameterMapBuilder.build();
		return this.parameterMap;
	}
	public ResultMap getResultMap(String entityName){
		if (this.resultMap!=null)return this.resultMap;
		
		ResultMap rm = null;
		if (entityName!=null){
			EntityMetadata em = getEntityMetadata(entityName);
			if (em!=null)rm = configuration.getResultMap(em.getMapId());
		}
		if (rm==null){
			//throw new SystemException("Result Map can not be null!");
			//List<ResultMap> resultMaps = new ArrayList<ResultMap>();
			ResultMap.Builder rmBuilder = new ResultMap.Builder(configuration, "", this.type, new ArrayList<ResultMapping>());
			rm = rmBuilder.build();
		}
		if (!this.type.equals(rm.getType())){
			ResultMap.Builder builder = new ResultMap.Builder(this.configuration,"",this.type,rm.getResultMappings());
			this.resultMap = builder.build();
		}else{
			this.resultMap = rm;
		}
		return resultMap;
	}
	@Override
	public ResultMap getResultMap() {
		return this.resultMap;
	}

	@Override
	public ParameterMap getParameterMap() {
		return this.parameterMap;
	}

	@Override
	public String getSql() {
		return this.sql;
	}
	public Object getParameterObject(){
		return this.parameterObject;
	}

}
