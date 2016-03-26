package com.payadd.framework.ddl.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import com.payadd.framework.common.exception.SystemException;


public class EntityMetadata implements java.io.Serializable{
	private Configuration configuration;
	private String entityName;
	private String className;
	private String table;
	private String mapId;
	private Class type;
	private String securityId;
	private ResultMap resultMap;
	private Map<String,Property> propertyMap;
	private Map<String,Property> columnPropertyMap;
	
	public Property getProperty(String propertyName){	
		if (propertyMap==null){
			genMap();
		}
		return this.propertyMap.get(propertyName);
	}
	public Property getPropertyByColumn(String columnName){
		if (columnPropertyMap==null){
			genMap();
		}
		return this.columnPropertyMap.get(columnName.toUpperCase());
	}
	private void genMap(){
		propertyMap = new HashMap<String,Property>();
		columnPropertyMap = new HashMap<String,Property>();
		ResultMap rm = getResultMap();
		List<ResultMapping> rms = rm.getResultMappings();
		for (int i=0;i<rms.size();i++){
			ResultMapping mapping = rms.get(i);
			Property p = new Property(mapping);
			propertyMap.put(mapping.getProperty(), p);
			columnPropertyMap.put(mapping.getColumn().toUpperCase(), p);
		}
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
		try {
			type = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new SystemException(e);
		}
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public Class getType() {
		return type;
	}
	public void setType(Class type) {
		this.type = type;
	}
	public String getMapId() {
		return mapId;
	}
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	public ResultMap getResultMap() {
		if (this.resultMap==null){
			this.resultMap = configuration.getResultMap(this.mapId);
		}
		return resultMap;
	}
	public void setResultMap(ResultMap resultMap) {
		this.resultMap = resultMap;
	}

}
