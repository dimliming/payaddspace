package com.payadd.framework.ddl.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import com.payadd.framework.common.exception.SystemException;

public class DeleteMappedStatementBuilder extends MappedStatementBuilder {
	private Class type;
	private String entityName;
	private Object id;
	private ResultMap resultMap;
	private EntityMetadata metadata;
	private ParameterMap parameterMap;
	
	public DeleteMappedStatementBuilder(Configuration configuration,Class type,Object id){
		this.configuration  = configuration;
		this.type = type;
		this.id = id;
		this.entityName = type.getName();
	}
	public DeleteMappedStatementBuilder(Configuration configuration,String entityName,Object id){
		this.configuration  = configuration;
		this.entityName = entityName;
		this.id = id;
	}
	public EntityMetadata getEntityMetadata(){
		if (metadata!=null)return metadata;
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
		this.resultMap = rm;
		return this.resultMap;
	}

	@Override
	public ParameterMap getParameterMap() {
		if (this.parameterMap!=null)return this.parameterMap;
		List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
		ResultMap rm = getResultMap();
		List<ResultMapping> idList = rm.getIdResultMappings();
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

	@Override
	public String getSql() {
		ResultMap rm = getResultMap();
		
		StringBuffer sql = new StringBuffer("delete from "+this.metadata.getTable()+" where ");
		
		List<ResultMapping> idList = rm.getIdResultMappings();
		for (int i=0;i<idList.size();i++){
			ResultMapping mapping = idList.get(i);
			String column = mapping.getColumn();
			if (i>0){
				sql.append(" and ");
			}
			sql.append(column).append("=?");
		}

		
		return sql.toString();
	}

}
