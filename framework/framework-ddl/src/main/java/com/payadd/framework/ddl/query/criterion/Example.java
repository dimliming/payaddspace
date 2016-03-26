package com.payadd.framework.ddl.query.criterion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Example implements Criterion {
	private final Object entity;
	private final Set excludedProperties = new HashSet();
	private PropertySelector selector;
	private boolean isLikeEnabled;
	private Character escapeCharacter;
	private boolean isIgnoreCaseEnabled;
	private MatchMode matchMode;

	/**
	 * A strategy for choosing property values for inclusion in the query
	 * criteria
	 */

	public static interface PropertySelector extends Serializable {
		public boolean include(Object propertyValue, String propertyName);
	}

	private static final PropertySelector NOT_NULL = new NotNullPropertySelector();
	private static final PropertySelector ALL = new AllPropertySelector();
	private static final PropertySelector NOT_NULL_OR_ZERO = new NotNullOrZeroPropertySelector();

	static final class AllPropertySelector implements PropertySelector {
		public boolean include(Object object, String propertyName) {
			return true;
		}
		
		private Object readResolve() {
			return ALL;
		}
	}

	static final class NotNullPropertySelector implements PropertySelector {
		public boolean include(Object object, String propertyName) {
			return object!=null&&!"".equals(object);
		}
		
		private Object readResolve() {
			return NOT_NULL;
		}
	}

	static final class NotNullOrZeroPropertySelector implements PropertySelector {
		public boolean include(Object object, String propertyName) {
			return object!=null && (
				!(object instanceof Number) || ( (Number) object ).longValue()!=0
			);
		}
		
		private Object readResolve() {
			return NOT_NULL_OR_ZERO;
		}
	}

	/**
	 * Set escape character for "like" clause
	 */
	public Example setEscapeCharacter(Character escapeCharacter) {
		this.escapeCharacter = escapeCharacter;
		return this;
	}

	/**
	 * Set the property selector
	 */
	public Example setPropertySelector(PropertySelector selector) {
		this.selector = selector;
		return this;
	}

	/**
	 * Exclude zero-valued properties
	 */
	public Example excludeZeroes() {
		setPropertySelector(NOT_NULL_OR_ZERO);
		return this;
	}

	/**
	 * Don't exclude null or zero-valued properties
	 */
	public Example excludeNone() {
		setPropertySelector(ALL);
		return this;
	}

	/**
	 * Use the "like" operator for all string-valued properties
	 */
	public Example enableLike(MatchMode matchMode) {
		isLikeEnabled = true;
		this.matchMode = matchMode;
		return this;
	}

	/**
	 * Use the "like" operator for all string-valued properties
	 */
	public Example enableLike() {
		return enableLike(MatchMode.ANYWHERE);
	}

	/**
	 * Ignore case for all string-valued properties
	 */
	public Example ignoreCase() {
		isIgnoreCaseEnabled = true;
		return this;
	}
	public Example caseSensitive() {
		isIgnoreCaseEnabled = false;
		return this;
	}

	/**
	 * Exclude a particular named property
	 */
	public Example excludeProperty(String name) {
		excludedProperties.add(name);
		return this;
	}

	/**
	 * Create a new instance, which includes all non-null properties
	 * by default
	 * @param entity
	 * @return a new instance of <tt>Example</tt>
	 */
	public static Example create(Object entity) {
		if (entity==null) throw new NullPointerException("null example");
		return new Example(entity, NOT_NULL);
	}

	protected Example(Object entity, PropertySelector selector) {
		this.entity = entity;
		this.selector = selector;
	}

	public String toString() {
		return "example (" + entity + ')';
	}

	private boolean isPropertyIncluded(Object value, String name) {
		return !excludedProperties.contains(name) &&
			selector.include(value, name);
	}

	public String toSqlString(Criteria criteria){

		StringBuffer buf = new StringBuffer().append('(');
		//PropertyMap resultMap = criteria.getPropertyMap();
		//ResultMapping[] mappings = resultMap.getResultMappings();
		CriteriaContext context = criteria.getCriteriaContext();
		String[] properties = context.allProperties();
		
		for (int i=0; i<properties.length; i++) {
			//ResultMapping mapping = mappings[i];
			String propertyName = properties[i];
			//String columnName = mapping.getColumnName();
			String columnName = criteria.getColumnName(propertyName);//ʹ���������Ի�ȡ��ǰ׺��columnName�����б�ı���
			//Property prop = resultMap.getProperty(propertyName);
			//Object propertyValue = prop.get(entity);
			Object propertyValue = context.get(entity, propertyName);
			
			boolean isPropertyIncluded = isPropertyIncluded( propertyValue, propertyName);
			if (!isPropertyIncluded)continue;

			appendPropertyCondition(
				propertyName,columnName,
				propertyValue,
				criteria,
				buf
			);
		}
		if ( buf.length()==1 ) buf.append("1=1"); //yuck!
		return buf.append(')').toString();
	}

	public List getParameters(Criteria criteria){
		//PropertyMap resultMap = criteria.getPropertyMap();
		//ResultMapping[] mappings = resultMap.getResultMappings();
		
		CriteriaContext context = criteria.getCriteriaContext();
		String[] properties = context.allProperties();
		
		List list = new ArrayList();
		for (int i=0; i<properties.length; i++) {
			//ResultMapping mapping = mappings[i];
			String propertyName = properties[i];
			//Property prop = resultMap.getProperty(propertyName);
			//Object propertyValue = prop.get(entity);
			Object propertyValue = context.get(entity, propertyName);
			
			boolean isPropertyIncluded = isPropertyIncluded(propertyValue, propertyName);
			if (!isPropertyIncluded)continue;
			
			addPropertyValue(propertyValue,list);
		}
		return list;
	}

	protected void addPropertyValue(Object value, List list) {
		if (value==null)return;
		if ( value instanceof String ) {
			String string = (String) value;
			if (value!=null&&isLikeEnabled){
				string = string.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
			}
			if (isIgnoreCaseEnabled)string = string.toLowerCase();
			if (isLikeEnabled) string = matchMode.toMatchString(string);
			value = string;
		}
		list.add(value);
	}

	protected void appendPropertyCondition(String propertyName,
		String columnName,Object propertyValue,Criteria criteria,StringBuffer buf){
		String sql = null;
		if ( propertyValue!=null||!"".equals(propertyValue) ) {
			boolean isString = propertyValue instanceof String;
			if (isString && isIgnoreCaseEnabled){
				columnName = "lower("+columnName+")";
			}
			if ( isLikeEnabled && isString ) {
				sql = columnName+" like ? ";
			}
			else {
				sql = columnName+" = ? ";
			}
		}
		else {
			sql = columnName+" is null ";
		}
		if ( buf.length()>1 && sql.trim().length()>0 ) buf.append(" and ");
		buf.append(sql);
	}

}
