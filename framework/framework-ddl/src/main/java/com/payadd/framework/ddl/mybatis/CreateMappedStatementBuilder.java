package com.payadd.framework.ddl.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import com.payadd.framework.common.exception.SystemException;

public class CreateMappedStatementBuilder extends MappedStatementBuilder {
	protected String entityName ;
	protected Class type;
	protected Object entity;
	protected EntityMetadata metadata;
	protected ResultMap resultMap;
	protected ParameterMap parameterMap;
	
	public CreateMappedStatementBuilder(Configuration configuration,String entityName,Object entity){
		this.configuration = configuration;
		this.entityName = entityName;
		this.entity = entity;
		getEntityMetadata();
		try {
			type = Class.forName(metadata.getClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		
	}
	public CreateMappedStatementBuilder(Configuration configuration,Class type,Object entity){
		this.configuration = configuration;
		this.type = type;
		this.entity = entity;
	}
	public String getEntityName(){
		if (entityName!=null)return entityName;
		if (type!=null){
			entityName = type.getName();
			return entityName;
		}
		throw new SystemException("entity name can not be null!");
	}
	public EntityMetadata getEntityMetadata(){
		if (metadata!=null)return metadata;
		String entityName = getEntityName();
		metadata = ((MyConfiguration)this.configuration).getEntityMap().get(entityName);
		return metadata;
	}
	@Override
	public ResultMap getResultMap() {
		if (this.resultMap!=null)return this.resultMap;
		EntityMetadata em = getEntityMetadata();
		ResultMap rm = configuration.getResultMap(em.getMapId());
		if (rm==null){
			throw new SystemException("Result Map can not be null!");
		}
		if (!this.type.equals(rm.getType())){
			ResultMap.Builder builder = new ResultMap.Builder(this.configuration,"",this.type,rm.getResultMappings());
			this.resultMap = builder.build();
		}else{
			this.resultMap = rm;
		}
		return this.resultMap;
	}

	@Override
	public ParameterMap getParameterMap() {
		if (this.parameterMap!=null)return this.parameterMap;
		List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
		ResultMap rm = getResultMap();
		List<ResultMapping> rmList = rm.getPropertyResultMappings();
		for (int i=0;i<rmList.size();i++){
			ResultMapping mapping = rmList.get(i);
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

	@Override
	public String getSql() {
		ResultMap rm = getResultMap();
		List<ResultMapping> rmList = rm.getPropertyResultMappings();
		StringBuffer sql = new StringBuffer("insert into "+this.metadata.getTable()+"(");
		StringBuffer valueStr = new StringBuffer(" value(");
		
		for (int i=0;i<rmList.size();i++){
			ResultMapping mapping = rmList.get(i);
			mapping.getColumn();
			if (i==0){
				sql.append(mapping.getColumn());
				valueStr.append("?");
			}else{
				sql.append(",").append(mapping.getColumn());
				valueStr.append(",?");
			}
		}
		sql.append(")");
		valueStr.append(")");
		sql.append(valueStr);
		
		return sql.toString();
	}

}
