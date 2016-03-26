package com.payadd.framework.ddl.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

public class UpdateMappedStatementBuilder extends CreateMappedStatementBuilder {

	public UpdateMappedStatementBuilder(Configuration configuration,Class type, Object entity) {
		super(configuration, type, entity);
	}
	public UpdateMappedStatementBuilder(Configuration configuration,String entityName, Object entity) {
		super(configuration, entityName, entity);
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
		List<ResultMapping> idMappings = rm.getIdResultMappings();
		for (int i=0;i<idMappings.size();i++){
			ResultMapping mapping = idMappings.get(i);
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
		StringBuffer sql = new StringBuffer("update "+this.getEntityMetadata().getTable()+" set ");
		
		for (int i=0;i<rmList.size();i++){
			ResultMapping mapping = rmList.get(i);
			String column = mapping.getColumn();
			if (i>0){
				sql.append(",");
			}
			sql.append(column).append("=?");
		}
		sql.append(" where ");
		List<ResultMapping> idMappings = rm.getIdResultMappings();
		for (int i=0;i<idMappings.size();i++){
			ResultMapping mapping = idMappings.get(i);
			String column = mapping.getColumn();
			if (i>0){
				sql.append(" and ");
			}
			sql.append(column).append("=?");
		}

		
		return sql.toString();
	}

}
