package com.payadd.framework.ddl.mybatis;

import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;

import com.payadd.framework.ddl.query.criterion.CriteriaContext;
import com.payadd.framework.common.exception.SystemException;



public class IbatisContext implements CriteriaContext {
	private EntityMetadata metadata;
	
	public IbatisContext(EntityMetadata metadata){
		this.metadata = metadata;
	}
	public String[] allColumns() {
		List<ResultMapping> rms = metadata.getResultMap().getResultMappings();
		String[] cols = new String[rms.size()];
		for (int i=0;i<rms.size();i++){
			cols[i]=rms.get(i).getColumn();
		}
		return cols;
	}

	public String[] allProperties() {
		List<ResultMapping> rms = metadata.getResultMap().getResultMappings();
		String[] props = new String[rms.size()];
		for (int i=0;i<rms.size();i++){
			props[i] = rms.get(i).getProperty();
		}
		return props;
	}

	public Object get(Object obj, String property) {
		Property p = metadata.getProperty(property);
		if (p==null)throw new SystemException("can't find property "+property+" in class "+obj.getClass().getName());
		return p.get(obj);
	}

	public String getColumnName(String propertyName) {
		Property p = metadata.getProperty(propertyName);
		if (p==null)throw new SystemException("can't find property "+propertyName);
		return p.getColumnName();
	}

	public void set(Object obj, String property, Object value) {
		Property p = metadata.getProperty(property);
		if (p==null)throw new SystemException("can't find property "+property+" in class "+obj.getClass().getName());
		p.set(obj, value);
	}
	public Class getReturnType() {
		return metadata.getType();
	}
	public String getTable() {
		return metadata.getTable();
	}
	public String getSecurityId() {
		return metadata.getSecurityId();
	}
	public Object getByColumn(Object obj, String column) {
		Property p = metadata.getPropertyByColumn(column);
		if (p==null)throw new SystemException("can't find column "+column+" in class "+obj.getClass().getName());
		return p.get(obj);
	}
	public String getPropertyName(String columnName) {
		Property p = metadata.getPropertyByColumn(columnName);
		if (p==null)throw new SystemException("can't find column "+columnName);
		return p.getPropertyName();
	}

}
