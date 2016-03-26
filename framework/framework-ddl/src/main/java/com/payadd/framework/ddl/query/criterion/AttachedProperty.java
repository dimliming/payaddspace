package com.payadd.framework.ddl.query.criterion;

public class AttachedProperty extends PropertyProjection {
	private final Criteria attachedCriteria;
	
	public AttachedProperty(Criteria c,String propertyName,boolean isGroup){
		super(propertyName,isGroup);
		this.attachedCriteria = c;
	}
	public AttachedProperty(Criteria c,String propertyName,String alias){
		super(propertyName,alias);
		this.attachedCriteria = c;
	}
	public AttachedProperty(Criteria c,String propertyName){
		super(propertyName);
		this.attachedCriteria = c;
	}
	
	public String toGroupSqlString(Criteria c) {
		if (isGroup()){
			return attachedCriteria.getColumnName(getPropertyName());
		}else{
			return null;
		}
	}

	public String toSqlString(Criteria c) {
		String columnName = attachedCriteria.getColumnName(getPropertyName());
		StringBuffer sql = new StringBuffer();
		sql.append(columnName);
		if (getAlias()!=null)sql.append(" ").append(getAlias());
		return sql.toString();
	}
}
