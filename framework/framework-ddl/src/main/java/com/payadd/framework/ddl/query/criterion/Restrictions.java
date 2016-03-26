package com.payadd.framework.ddl.query.criterion;

import java.util.List;

public class Restrictions {

	Restrictions() {
		//cannot be instantiated
	}

	/**
	 * Apply an "equal" constraint to the identifier property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
//	public static Criterion idEq(Object value) {
//		return new IdentifierEqExpression(value);
//	}
	/**
	 * Apply an "equal" constraint to the named property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression eq(String propertyName, Object value) {
		return new SimpleExpression(propertyName, "=", value);
	}
	/**
	 * Apply a "not equal" constraint to the named property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression ne(String propertyName, Object value) {
		return new SimpleExpression(propertyName, "<>", value);
	}
	/**
	 * Apply a "like" constraint to the named property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression like(String propertyName, Object value) {
		if (value!=null){
			String s = (String)value;
			value = s.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		}
		return new SimpleExpression(propertyName, " like ", "%"+value+"%");
	}
	/**
	 * Apply a "like" constraint to the named property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression startsWith(String propertyName, Object value) {
		if (value!=null){
			String s = (String)value;
			value = s.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		}
		return new SimpleExpression(propertyName, " like ", value+"%");
	}
	/**
	 * Apply a "like" constraint to the named property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression endsWith(String propertyName, Object value) {
		if (value!=null){
			String s = (String)value;
			value = s.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		}
		return new SimpleExpression(propertyName, " like ", "%"+value);
	}
	
	/**
	 * Apply a "not like" constraint to the named property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression notLike(String propertyName, Object value) {
		if (value!=null){
			String s = (String)value;
			value = s.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		}
		return new SimpleExpression(propertyName, " not like ", "%"+value+"%");
	}
	/**
	 * Apply a "like" constraint to the named property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression like(String propertyName, String value, MatchMode matchMode) {
		if (value!=null){
			String s = (String)value;
			value = s.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		}
		return new SimpleExpression(propertyName, " like " , matchMode.toMatchString(value));
	}
	/**
	 * A case-insensitive "like", similar to Postgres <tt>ilike</tt>
	 * operator
	 *
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
//	public static Criterion ilike(String propertyName, String value, MatchMode matchMode) {
//		return new IlikeExpression(propertyName, value, matchMode);
//	}
	/**
	 * A case-insensitive "like", similar to Postgres <tt>ilike</tt>
	 * operator
	 *
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
//	public static Criterion ilike(String propertyName, Object value) {
//		return new IlikeExpression(propertyName, value);
//	}
	/**
	 * Apply a "greater than" constraint to the named property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression gt(String propertyName, Object value) {
		return new SimpleExpression(propertyName, ">", value);
	}
	/**
	 * Apply a "less than" constraint to the named property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression lt(String propertyName, Object value) {
		return new SimpleExpression(propertyName, "<", value);
	}
	/**
	 * Apply a "less than or equal" constraint to the named property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression le(String propertyName, Object value) {
		return new SimpleExpression(propertyName, "<=", value);
	}
	/**
	 * Apply a "greater than or equal" constraint to the named property
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression ge(String propertyName, Object value) {
		return new SimpleExpression(propertyName, ">=", value);
	}
	/**
	 * Apply a "between" constraint to the named property
	 * @param propertyName
	 * @param lo value
	 * @param hi value
	 * @return Criterion
	 */
	public static Criterion between(String propertyName, Object lo, Object hi) {
		return new BetweenExpression(propertyName, lo, hi);
	}
	/**
	 * Apply an "in" constraint to the named property
	 * @param propertyName
	 * @param values
	 * @return Criterion
	 */
	public static Criterion in(String propertyName, String values) {
		return new InExpression(propertyName, values);
	}
	/**
	 * Apply an "in" constraint to the named property
	 * @param propertyName
	 * @param values
	 * @return Criterion
	 */
//	public static Criterion in(String propertyName, Collection values) {
//		return new InExpression( propertyName, values.toArray() );
//	}
	/**
	 * Apply an "is null" constraint to the named property
	 * @return Criterion
	 */
	public static Criterion isNull(String propertyName) {
		return new NullExpression(propertyName);
	}
	/**
	 * Apply an "equal" constraint to two properties
	 */
	public static PropertyExpression eqProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, "=", otherPropertyName);
	}
	/**
	 * Apply a "not equal" constraint to two properties
	 */
	public static PropertyExpression neProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, "<>", otherPropertyName);
	}
	/**
	 * Apply a "less than" constraint to two properties
	 */
	public static PropertyExpression ltProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, "<", otherPropertyName);
	}
	/**
	 * Apply a "less than or equal" constraint to two properties
	 */
	public static PropertyExpression leProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, "<=", otherPropertyName);
	}
	/**
	 * Apply a "greater than" constraint to two properties
	 */
	public static PropertyExpression gtProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, ">", otherPropertyName);
	}
	/**
	 * Apply a "greater than or equal" constraint to two properties
	 */
	public static PropertyExpression geProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, ">=", otherPropertyName);
	}

	
/***/
	/**
	 * Apply an "equal" constraint to two properties
	 */
	public static PropertyExpression eqProperty(PropertyProjection property, PropertyProjection otherProperty) {
		return new PropertyExpression(property, "=", otherProperty);
	}
	/**
	 * Apply a "not equal" constraint to two properties
	 */
	public static PropertyExpression neProperty(PropertyProjection property, PropertyProjection otherProperty) {
		return new PropertyExpression(property, "<>", otherProperty);
	}
	/**
	 * Apply a "less than" constraint to two properties
	 */
	public static PropertyExpression ltProperty(PropertyProjection property, PropertyProjection otherProperty) {
		return new PropertyExpression(property, "<", otherProperty);
	}
	/**
	 * Apply a "less than or equal" constraint to two properties
	 */
	public static PropertyExpression leProperty(PropertyProjection property, PropertyProjection otherProperty) {
		return new PropertyExpression(property, "<=", otherProperty);
	}
	/**
	 * Apply a "greater than" constraint to two properties
	 */
	public static PropertyExpression gtProperty(PropertyProjection property, PropertyProjection otherProperty) {
		return new PropertyExpression(property, ">", otherProperty);
	}
	/**
	 * Apply a "greater than or equal" constraint to two properties
	 */
	public static PropertyExpression geProperty(PropertyProjection property, PropertyProjection otherProperty) {
		return new PropertyExpression(property, ">=", otherProperty);
	}
	/**
	 * Apply an "is not null" constraint to the named property
	 * @return Criterion
	 */
	public static Criterion isNotNull(String propertyName) {
		return new NotNullExpression(propertyName);
	}
	/**
	 * Return the conjuction of two expressions
	 *
	 * @param lhs
	 * @param rhs
	 * @return Criterion
	 */
//	public static LogicalExpression and(Criterion lhs, Criterion rhs) {
//		return new LogicalExpression(lhs, rhs, "and");
//	}
	/**
	 * Return the disjuction of two expressions
	 *
	 * @param lhs
	 * @param rhs
	 * @return Criterion
	 */
//	public static LogicalExpression or(Criterion lhs, Criterion rhs) {
//		return new LogicalExpression(lhs, rhs, "or");
//	}
	/**
	 * Return the negation of an expression
	 *
	 * @param expression
	 * @return Criterion
	 */
//	public static Criterion not(Criterion expression) {
//		return new NotExpression(expression);
//	}
	/**
	 * Apply a constraint expressed in SQL, with the given JDBC
	 * parameters. Any occurrences of <tt>{alias}</tt> will be
	 * replaced by the table alias.
	 *
	 * @param sql
	 * @param values
	 * @param types
	 * @return Criterion
	 */
	public static Criterion sqlRestriction(String sql, List values) {
		return new SqlExpression(sql, values);
	}

	/**
	 * Group expressions together in a single conjunction (A and B and C...)
	 *
	 * @return Conjunction
	 */
	public static Conjunction conjunction() {
		return new Conjunction();
	}

	/**
	 * Group expressions together in a single disjunction (A or B or C...)
	 *
	 * @return Conjunction
	 */
	public static Disjunction disjunction() {
		return new Disjunction();
	}

	/**
	 * Apply an "equals" constraint to each property in the
	 * key set of a <tt>Map</tt>
	 *
	 * @param propertyNameValues a map from property names to values
	 * @return Criterion
	 */
//	public static Criterion allEq(Map propertyNameValues) {
//		Conjunction conj = conjunction();
//		Iterator iter = propertyNameValues.entrySet().iterator();
//		while ( iter.hasNext() ) {
//			Map.Entry me = (Map.Entry) iter.next();
//			conj.add( eq( (String) me.getKey(), me.getValue() ) );
//		}
//		return conj;
//	}
	
	/**
	 * Constrain a collection valued property to be empty
	 */
//	public static Criterion isEmpty(String propertyName) {
//		return new EmptyExpression(propertyName);
//	}

	/**
	 * Constrain a collection valued property to be non-empty
	 */
//	public static Criterion isNotEmpty(String propertyName) {
//		return new NotEmptyExpression(propertyName);
//	}
	
	/**
	 * Constrain a collection valued property by size
	 */
//	public static Criterion sizeEq(String propertyName, int size) {
//		return new SizeExpression(propertyName, size, "=");
//	}
	
	/**
	 * Constrain a collection valued property by size
	 */
//	public static Criterion sizeNe(String propertyName, int size) {
//		return new SizeExpression(propertyName, size, "<>");
//	}
	
	/**
	 * Constrain a collection valued property by size
	 */
//	public static Criterion sizeGt(String propertyName, int size) {
//		return new SizeExpression(propertyName, size, "<");
//	}
	
	/**
	 * Constrain a collection valued property by size
	 */
//	public static Criterion sizeLt(String propertyName, int size) {
//		return new SizeExpression(propertyName, size, ">");
//	}
	
	/**
	 * Constrain a collection valued property by size
	 */
//	public static Criterion sizeGe(String propertyName, int size) {
//		return new SizeExpression(propertyName, size, "<=");
//	}
	
	/**
	 * Constrain a collection valued property by size
	 */
//	public static Criterion sizeLe(String propertyName, int size) {
//		return new SizeExpression(propertyName, size, ">=");
//	}
	
//	public static NaturalIdentifier naturalId() {
//		return new NaturalIdentifier();
//	}
}
