package com.payadd.framework.ddl.query.criterion;

public class Projections {
	private static final AllProjection allProjection = new AllProjection();
	/**
	 * Create a distinct projection from a projection
	 */
	public static Projection distinct(Projection proj) {
		return new Distinct(proj);
	}
	
	/**
	 * Create a new projection list
	 */
	public static ProjectionList projectionList() {
		return new ProjectionList();
	}
		
	/**
	 * The query row count, ie. <tt>count(*)</tt>
	 */
	public static RowCountProjection rowCount() {
		return new RowCountProjection();
	}
	
	/**
	 * A property value count
	 */
	public static CountProjection count(String propertyName) {
		return new CountProjection(propertyName);
	}
	
	/**
	 * A distinct property value count
	 */
	public static CountProjection countDistinct(String propertyName) {
		return new CountProjection(propertyName).setDistinct();
	}
	
	/**
	 * A property maximum value
	 */
	public static AggregateProjection max(String propertyName) {
		return new AggregateProjection("max", propertyName);
	}
	
	/**
	 * A property minimum value
	 */
	public static AggregateProjection min(String propertyName) {
		return new AggregateProjection("min", propertyName);
	}
	
	/**
	 * A property average value
	 */
//	public static AggregateProjection avg(String propertyName) {
//		return new AvgProjection(propertyName);
//	}
	
	/**
	 * A property value sum
	 */
	public static AggregateProjection sum(String propertyName) {
		return new AggregateProjection("sum", propertyName);
	}
	
	/**
	 * A SQL projection, a typed select clause fragment
	 */
//	public static Projection sqlProjection(String sql, String[] columnAliases, Type[] types) {
//		return new SQLProjection(sql, columnAliases, types);
//	}
	
	/**
	 * A grouping SQL projection, specifying both select clause and group by clause fragments
	 */
//	public static Projection sqlGroupProjection(String sql, String groupBy, String[] columnAliases, Type[] types) {
//		return new SQLProjection(sql, groupBy, columnAliases, types);
//	}

	/**
	 * A grouping property value
	 */
	public static PropertyProjection groupProperty(String propertyName) {
		return new PropertyProjection(propertyName, true);
	}
	public static PropertyProjection groupProperty(String propertyName,int priority) {
		return new PropertyProjection(propertyName, true,priority);
	}
	
	/**
	 * A projected property value
	 */
	public static PropertyProjection property(String propertyName) {
		return new PropertyProjection(propertyName);
	}
	public static PropertyProjection property(String propertyName,String alias) {
		return new PropertyProjection(propertyName,alias);
	}
	/**
	 * A projected identifier value
	 */
//	public static IdentifierProjection id() {
//		return new IdentifierProjection();
//	}
	
	/**
	 * Assign an alias to a projection, by wrapping it
	 */
//	public static Projection alias(Projection projection, String alias) {
//		return new AliasedProjection(projection, alias);
//	}
	public static Projection allProjection(){
		return allProjection;
	}
	public static SqlProjection sqlProjection(String sql){
		return new SqlProjection(sql);
	}
	public static Projection excludeProperty(String propertyNames){
		return new ExcludeProjection(propertyNames);
	}
	public static AttachedProperty attachedProperty(Criteria c,String propertyName,boolean isGroup){
		return new AttachedProperty(c,propertyName,isGroup);
	}
	public static AttachedProperty attachedProperty(Criteria c,String propertyName,String alias){
		return new AttachedProperty(c,propertyName,alias);
	}
	public static AttachedProperty attachedProperty(Criteria c,String propertyName){
		return new AttachedProperty(c,propertyName);
	}
}
