package com.payadd.framework.ddl.query.criterion;

import java.util.HashMap;
import java.util.Map;

public class ExcludeProjection implements Projection {
	private Map excludePropertyMap = new HashMap();
	
	public ExcludeProjection(String propertyNames){
		String[] props = propertyNames.split(",");
		for (int i=0;i<props.length;i++){
			excludePropertyMap.put(props[i], props[i]);
		}
	}
	public void add(String propertyName){
		excludePropertyMap.put(propertyName, propertyName);
	}
	public String[] getColumnAliases() {
		return null;
	}

	public boolean isGrouped() {
		return false;
	}

	public String toGroupSqlString(Criteria c) {
		return null;
	}

	public String toSqlString(Criteria c) {
		StringBuffer sql = new StringBuffer();
		//ResultMapping[] mappings = c.getEntityMetadata().getPropertyMap().getResultMappings();
		CriteriaContext context = c.getCriteriaContext();
		String[] properties = context.allProperties(); 
		for (int i=0;i<properties.length;i++){
			//如果该属性包含在排除的列表中，那么直接跳过
			if (excludePropertyMap.containsKey(properties[i]))continue;
			if (sql.length()>0)sql.append(",").append(c.getPrefix()).append(properties[i]);
			else sql.append(c.getPrefix()).append(context.getColumnName(properties[i]));
		}
		return sql.toString();
	}
	public int getPriority() {
		return -1;
	}
}
