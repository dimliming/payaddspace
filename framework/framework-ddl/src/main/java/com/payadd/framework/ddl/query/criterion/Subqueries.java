package com.payadd.framework.ddl.query.criterion;

public class Subqueries {
	public static Criterion exists(Criteria c) {
		return new ExistsSubqueryExpression("exists", c);
	}
	
	public static Criterion notExists(Criteria c) {
		return new ExistsSubqueryExpression("not exists", c);
	}
	
	public static Criterion propertyEqAll(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, "=", "all", c);
	}
	
	public static Criterion propertyIn(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, "in", null, c);
	}
	
	public static Criterion propertyNotIn(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, "not in", null, c);
	}
	
	public static Criterion propertyEq(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, "=", null, c);
	}
	
	public static Criterion propertyNe(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, "<>", null, c);
	}
	
	public static Criterion propertyGt(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, ">", null, c);
	}
	
	public static Criterion propertyLt(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, "<", null, c);
	}
	
	public static Criterion propertyGe(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, ">=", null, c);
	}
	
	public static Criterion propertyLe(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, "<=", null, c);
	}
	
	public static Criterion propertyGtAll(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, ">", "all", c);
	}
	
	public static Criterion propertyLtAll(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, "<", "all", c);
	}
	
	public static Criterion propertyGeAll(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, ">=", "all", c);
	}
	
	public static Criterion propertyLeAll(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, "<=", "all", c);
	}
	
	public static Criterion propertyGtSome(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, ">", "some", c);
	}
	
	public static Criterion propertyLtSome(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, "<", "some", c);
	}
	
	public static Criterion propertyGeSome(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, ">=", "some", c);
	}
	
	public static Criterion propertyLeSome(String propertyName, Criteria c) {
		return new PropertySubqueryExpression(propertyName, "<=", "some", c);
	}
	
	public static Criterion eqAll(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, "=", "all", c);
	}
	
	public static Criterion in(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, "in", null, c);
	}
	
	public static Criterion notIn(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, "not in", null, c);
	}
	
	public static Criterion eq(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, "=", null, c);
	}
	
	public static Criterion gt(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, ">", null, c);
	}
	
	public static Criterion lt(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, "<", null, c);
	}
	
	public static Criterion ge(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, ">=", null, c);
	}
	
	public static Criterion le(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, "<=", null, c);
	}
	
	public static Criterion ne(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, "<>", null, c);
	}
	
	public static Criterion gtAll(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, ">", "all", c);
	}
	
	public static Criterion ltAll(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, "<", "all", c);
	}
	
	public static Criterion geAll(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, ">=", "all", c);
	}
	
	public static Criterion leAll(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, "<=", "all", c);
	}
	
	public static Criterion gtSome(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, ">", "some", c);
	}
	
	public static Criterion ltSome(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, "<", "some", c);
	}
	
	public static Criterion geSome(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, ">=", "some", c);
	}
	
	public static Criterion leSome(Object value, Criteria c) {
		return new SimpleSubqueryExpression(value, "<=", "some", c);
	}
}
