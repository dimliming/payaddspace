package com.payadd.framework.ddl.query.criterion;


public class Order implements Priority,java.io.Serializable{

	private boolean ascending;
	private boolean ignoreCase;
	private String propertyName;
	private int priority;//字段排序的优先级，-1表示按照默认的优先级，默认的优先级就是设置排序字段的时候的设置顺序
	private Criteria belongToCriteria;
	
	public String toString() {
		return propertyName + ' ' + (ascending?"asc":"desc");
	}
	
	public Order ignoreCase() {
		ignoreCase = true;
		return this;
	}

	protected Order(String propertyName, boolean ascending,int priority) {
		this.propertyName = propertyName;
		this.ascending = ascending;
		this.priority = priority;
	}

	public String toSqlString() {
		String columnName = belongToCriteria.getColumnName(propertyName);
		if (ascending){
			return columnName+" asc";
		}else{
			return columnName+" desc";
		}
	}


	public static Order asc(String propertyName) {
		return new Order(propertyName, true,-1);
	}

	public static Order desc(String propertyName) {
		return new Order(propertyName, false,-1);
	}
	public static Order sqlOrder(String orderSql) {
		return new SqlOrder(orderSql,-1);
	}
	public static Order asc(String propertyName,int orderPriority) {
		return new Order(propertyName, true,orderPriority);
	}

	public static Order desc(String propertyName,int orderPriority) {
		return new Order(propertyName, false,orderPriority);
	}
	public static Order sqlOrder(String orderSql,int orderPriority) {
		return new SqlOrder(orderSql,orderPriority);
	}

	public int getPriority() {
		return priority;
	}

	public void setBelongToCriteria(Criteria belongToCriteria) {
		this.belongToCriteria = belongToCriteria;
	}
}
