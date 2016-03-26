package com.payadd.framework.ddl.mybatis;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.mapping.ResultMapping;

import com.payadd.framework.common.exception.SystemException;



public class Property {
	private ResultMapping mapping;
	
	public Property(ResultMapping mapping){
		this.mapping = mapping;
	}

	/**
	 * convenient method for getter
	 */
	public Object get(Object target){
		try {
			return PropertyUtils.getProperty(target, getPropertyName());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		
	}
	/**
	 *convenient method for setter
	 */
	public void set(Object target,Object value){
		
		try {
			PropertyUtils.setProperty(target, getPropertyName(), value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new SystemException(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		
	}
	
	/********delegate method********/
//	public int getColumnIndex() {
//		return mapping.getColumnIndex();
//	}
	public String getColumnName() {
		return mapping.getColumn();
	}
//	public String getErrorString() {
//		return mapping.getErrorString();
//	}
//	public Class getJavaType() {
//		//return mapping.getJavaType();
//		if (!(getter instanceof MethodInvoker)){
//			throw new SystemException("you has forgotten to define the getter method of property "+mapping.getPropertyName());
//		}
//		return ((MethodInvoker)getter).getMethod().getReturnType();
//	}
//	public int getJdbcType() {
//		return mapping.getJdbcType();
//	}
//	public String getJdbcTypeName() {
//		return mapping.getJdbcTypeName();
//	}
//	public String getNestedResultMapName() {
//		return mapping.getNestedResultMapName();
//	}
//	public String getNotNullColumn() {
//		return mapping.getNotNullColumn();
//	}
//	public String getNullValue() {
//		return mapping.getNullValue();
//	}
	public String getPropertyName() {
		return mapping.getProperty();
	}
//	public String getStatementName() {
//		return mapping.getStatementName();
//	}
//	public TypeHandler getTypeHandler() {
//		return mapping.getTypeHandler();
//	}
//	public void setColumnIndex(int columnIndex) {
//		mapping.setColumnIndex(columnIndex);
//	}
//	public void setColumnName(String columnName) {
//		mapping.setColumnName(columnName);
//	}
//	public void setJavaType(Class javaType) {
//		mapping.setJavaType(javaType);
//	}
//	public void setJdbcTypeName(String jdbcTypeName) {
//		mapping.setJdbcTypeName(jdbcTypeName);
//	}
//	public void setNestedResultMapName(String nestedResultMapName) {
//		mapping.setNestedResultMapName(nestedResultMapName);
//	}
//	public void setNotNullColumn(String notNullColumn) {
//		mapping.setNotNullColumn(notNullColumn);
//	}
//	public void setNullValue(String nullValue) {
//		mapping.setNullValue(nullValue);
//	}
//	public void setPropertyName(String propertyName) {
//		mapping.setPropertyName(propertyName);
//	}
//	public void setStatementName(String statementName) {
//		mapping.setStatementName(statementName);
//	}
//	public void setTypeHandler(TypeHandler typeHandler) {
//		mapping.setTypeHandler(typeHandler);
//	}
}
