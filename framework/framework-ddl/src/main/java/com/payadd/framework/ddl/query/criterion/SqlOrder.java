package com.payadd.framework.ddl.query.criterion;


public class SqlOrder extends Order{

	private String orderSql;
	private int priority;//字段排序的优先级，-1表示按照默认的优先级，默认的优先级就是设置排序字段的时候的设置顺序


	protected SqlOrder(String orderSql,int priority) {
		super(null,false,priority);
		this.orderSql = orderSql;
		this.priority = priority;
	}

	public String toSqlString() {
		return orderSql;
	}


	public int getPriority() {
		return priority;
	}

}
