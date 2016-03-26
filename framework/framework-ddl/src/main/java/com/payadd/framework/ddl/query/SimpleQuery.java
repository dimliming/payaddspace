package com.payadd.framework.ddl.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.framework.ddl.DatabaseFacadeStack;
import com.payadd.framework.ddl.query.criterion.BetweenExpression;
import com.payadd.framework.ddl.query.criterion.Conjunction;
import com.payadd.framework.ddl.query.criterion.Criteria;
import com.payadd.framework.ddl.query.criterion.Criterion;
import com.payadd.framework.ddl.query.criterion.Disjunction;
import com.payadd.framework.ddl.query.criterion.Example;
import com.payadd.framework.ddl.query.criterion.JoinBridge;
import com.payadd.framework.ddl.query.criterion.MatchMode;
import com.payadd.framework.ddl.query.criterion.NotNullExpression;
import com.payadd.framework.ddl.query.criterion.NullExpression;
import com.payadd.framework.ddl.query.criterion.Order;
import com.payadd.framework.ddl.query.criterion.Projection;
import com.payadd.framework.ddl.query.criterion.ProjectionList;
import com.payadd.framework.ddl.query.criterion.Projections;
import com.payadd.framework.ddl.query.criterion.PropertyExpression;
import com.payadd.framework.ddl.query.criterion.PropertyProjection;
import com.payadd.framework.ddl.query.criterion.SimpleExpression;
import com.payadd.framework.ddl.query.criterion.SqlExpression;
import com.payadd.framework.common.exception.SystemException;



public class SimpleQuery implements QueryFacade{
	protected final Criteria criteria;
	private Example example;
	private boolean isIgnoreCaseEnable = false;
//	private IbatisClient client;//TODO: 如果配置了多个client，那么如何获取client呢？
	private DatabaseFacade facade;
	
	public SimpleQuery(DatabaseFacade facade,Object condition){
		this.facade = facade;
		example = Example.create(condition);
		criteria = new Criteria(condition.getClass().getName());
		criteria.add(example);
	}
	public SimpleQuery(DatabaseFacade facade,Class type){
		this.facade = facade;
		criteria = new Criteria(type.getName());
	}
	public SimpleQuery(DatabaseFacade facade,String entityName){
		this.facade = facade;
		criteria = new Criteria(entityName);
	}
	public void as(String alias){
		criteria.as(alias);
	}
	public void setCondition(Object condition){
		example = Example.create(condition);
		if (isIgnoreCaseEnable){
			example.ignoreCase();
		}
		criteria.add(example);
		//if (criteria!=null)criteria = new Criteria(condition.getClass());
	}
	public void enableLike(){
		if (example!=null){
			example.enableLike();
		}
	}
	public void enableLike(MatchMode matchMode){
		if (example!=null){
			example.enableLike(matchMode);
		}
	}
	public SimpleQuery ignoreCase(){
		isIgnoreCaseEnable = true;
		if (example!=null){
			example.ignoreCase();
		}
		return this;
	}
	
	public Criteria getCriteria() {
		return criteria;
	}

//	public IbatisClient getClient(){
//		client = IbatisClient.getInstance();
//		return client;
//	}
//	public DatabaseFacade getFacade(){
//		//先从当前线程的facade栈中获取，
//		DatabaseFacade contextFacade = DatabaseFacadeStack.peek();
//		if (contextFacade==null){
//			//如果没有获取到，就从当前线程获取，如果还没获取到，就从spring中直接读取
//			System.out.println("当前线程没有绑定DatabaseFacade，从spring xml获取");
//			this.facade = SpringUtil.getFacade();
//		}else{
//			System.out.println("当前线程绑定了DatabaseFacade");
//			this.facade = contextFacade;
//		}
//		return this.facade;
//	}
	public DatabaseFacade getFacade(){
		return this.facade;
	}
	//设置返回结果中包含的字段，多个字段之间用逗号隔开
	public SimpleQuery includeProjection(String propertyNames){
		ProjectionList pl = new ProjectionList();
		String[] props = propertyNames.split(",");
		for (int i=0;i<props.length;i++){
			pl.add(Projections.property(props[i]));
		}
		criteria.setProjection(pl);
		return this;
	}
	//设置返回结果中不包含的字段，多个字段之间用逗号隔开
	public SimpleQuery excludeProjection(String propertyNames){
		criteria.setProjection(Projections.excludeProperty(propertyNames));
		return this;
	}
	public SimpleQuery excludePropertyCriterion(String propertyNames){
		if (propertyNames==null||example==null)return this;
		String[] props = propertyNames.split(",");
		for (int i=0;i<props.length;i++){
			example.excludeProperty(props[i]);
		}
		return this;
	}
	public SimpleQuery orderByAsc(String propertyName){
		criteria.addOrder(Order.asc(propertyName));
		return this;
	}
	public SimpleQuery orderByDesc(String propertyName){
		criteria.addOrder(Order.desc(propertyName));
		return this;
	}
	public SimpleQuery sqlOrder(String orderSql){
		criteria.addOrder(Order.sqlOrder(orderSql));
		return this;
	}
	public SimpleQuery orderByAsc(String propertyName,int orderPriority){
		criteria.addOrder(Order.asc(propertyName,orderPriority));
		return this;
	}
	public SimpleQuery orderByDesc(String propertyName,int orderPriority){
		criteria.addOrder(Order.desc(propertyName,orderPriority));
		return this;
	}
	public SimpleQuery sqlOrder(String orderSql,int orderPriority){
		criteria.addOrder(Order.sqlOrder(orderSql,orderPriority));
		return this;
	}
	public SimpleQuery add(Criterion c){
		criteria.add(c);
		return this;
	}
	public SimpleQuery setProjection(Projection projection){
		criteria.setProjection(projection);
		return this;
	}
	public SimpleQuery leftjoin(SimpleQuery sq,String[] leftProperties,String[] rightProperties){
		this.criteria.leftjoin(sq.getCriteria(), leftProperties, rightProperties);
		return this;
	}
	public SimpleQuery innerjoin(SimpleQuery sq,String[] leftProperties,String[] rightProperties){
		this.criteria.innerjoin(sq.getCriteria(), leftProperties, rightProperties);
		return this;
	}
	public SimpleQuery rightjoin(SimpleQuery sq,String[] leftProperties,String[] rightProperties){
		this.criteria.rightjoin(sq.getCriteria(), leftProperties, rightProperties);
		return this;
	}
	public SimpleQuery fulljoin(SimpleQuery sq,String[] leftProperties,String[] rightProperties){
		this.criteria.fulljoin(sq.getCriteria(), leftProperties, rightProperties);
		return this;
	}
/****begin*****/

//	public  SimpleQuery idEq(Object value) {
//		criteria.add(new IdentifierEqExpression(value));
//		return this;
//	}

	public SimpleQuery eq(String propertyName, Object value) {
		criteria.add(new SimpleExpression(propertyName, "=", value));
		return this;
	}

	public SimpleQuery ne(String propertyName, Object value) {
		criteria.add(new SimpleExpression(propertyName, "<>", value));
		return this;
	}

	public SimpleQuery like(String propertyName, Object value) {
		if (value!=null){
			String s = (String)value;
			value = s.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		}
		criteria.add(new SimpleExpression(propertyName, " like ", value));
		return this;
	}

	public SimpleQuery like(String propertyName, String value, MatchMode matchMode) {
		if (value!=null){
			String s = (String)value;
			value = s.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		}
		criteria.add(new SimpleExpression(propertyName, " like " , matchMode.toMatchString(value)));
		return this;
	}

//	public SimpleQuery ilike(String propertyName, String value, MatchMode matchMode) {
//		criteria.add(new IlikeExpression(propertyName, value, matchMode));
//		return this;
//	}

//	public SimpleQuery ilike(String propertyName, Object value) {
//		criteria.add(new IlikeExpression(propertyName, value));
//		return this;
//	}

	public SimpleQuery gt(String propertyName, Object value) {
		criteria.add(new SimpleExpression(propertyName, ">", value));
		return this;
	}

	public SimpleQuery lt(String propertyName, Object value) {
		criteria.add(new SimpleExpression(propertyName, "<", value));
		return this;
	}

	public SimpleQuery le(String propertyName, Object value) {
		criteria.add(new SimpleExpression(propertyName, "<=", value));
		return this;
	}

	public SimpleQuery ge(String propertyName, Object value) {
		criteria.add(new SimpleExpression(propertyName, ">=", value));
		return this;
	}

	public SimpleQuery between(String propertyName, Object lo, Object hi) {
		criteria.add(new BetweenExpression(propertyName, lo, hi));
		return this;
	}

//	public SimpleQuery in(String propertyName, Object[] values) {
//		criteria.add(new InExpression(propertyName, values));
//		return this;
//	}

//	public SimpleQuery in(String propertyName, Collection values) {
//		criteria.add(new InExpression( propertyName, values.toArray() ));
//		return this;
//	}

	public SimpleQuery isNull(String propertyName) {
		criteria.add(new NullExpression(propertyName));
		return this;
	}

	public SimpleQuery eqProperty(String propertyName, String otherPropertyName) {
		criteria.add(new PropertyExpression(propertyName, "=", otherPropertyName));
		return this;
	}

	public SimpleQuery neProperty(String propertyName, String otherPropertyName) {
		criteria.add(new PropertyExpression(propertyName, "<>", otherPropertyName));
		return this;
	}

	public SimpleQuery ltProperty(String propertyName, String otherPropertyName) {
		criteria.add(new PropertyExpression(propertyName, "<", otherPropertyName));
		return this;
	}

	public SimpleQuery leProperty(String propertyName, String otherPropertyName) {
		criteria.add(new PropertyExpression(propertyName, "<=", otherPropertyName));
		return this;
	}

	public SimpleQuery gtProperty(String propertyName, String otherPropertyName) {
		criteria.add(new PropertyExpression(propertyName, ">", otherPropertyName));
		return this;
	}

	public SimpleQuery geProperty(String propertyName, String otherPropertyName) {
		criteria.add(new PropertyExpression(propertyName, ">=", otherPropertyName));
		return this;
	}

	
/***/

	public SimpleQuery eqProperty(PropertyProjection property, PropertyProjection otherProperty) {
		criteria.add(new PropertyExpression(property, "=", otherProperty));
		return this;
	}

	public SimpleQuery neProperty(PropertyProjection property, PropertyProjection otherProperty) {
		criteria.add(new PropertyExpression(property, "<>", otherProperty));
		return this;
	}

	public SimpleQuery ltProperty(PropertyProjection property, PropertyProjection otherProperty) {
		criteria.add(new PropertyExpression(property, "<", otherProperty));
		return this;
	}

	public SimpleQuery leProperty(PropertyProjection property, PropertyProjection otherProperty) {
		criteria.add(new PropertyExpression(property, "<=", otherProperty));
		return this;
	}

	public SimpleQuery gtProperty(PropertyProjection property, PropertyProjection otherProperty) {
		criteria.add(new PropertyExpression(property, ">", otherProperty));
		return this;
	}

	public SimpleQuery geProperty(PropertyProjection property, PropertyProjection otherProperty) {
		criteria.add(new PropertyExpression(property, ">=", otherProperty));
		return this;
	}

	public SimpleQuery isNotNull(String propertyName) {
		criteria.add(new NotNullExpression(propertyName));
		return this;
	}

//	public SimpleQuery and(Criterion lhs, Criterion rhs) {
//		criteria.add(new LogicalExpression(lhs, rhs, "and"));
//		return this;
//	}

//	public SimpleQuery or(Criterion lhs, Criterion rhs) {
//		criteria.add(new LogicalExpression(lhs, rhs, "or"));
//		return this;
//	}

//	public SimpleQuery not(Criterion expression) {
//		criteria.add(new NotExpression(expression));
//		return this;
//	}

	public SimpleQuery sqlRestriction(String sql, List values) {
		criteria.add(new SqlExpression(sql, values));
		return this;
	}

	public SimpleQuery conjunction() {
		criteria.add(new Conjunction());
		return this;
	}

	public SimpleQuery disjunction() {
		criteria.add(new Disjunction());
		return this;
	}
/****end*****/
	
	/**
	 * 数据查询方法，返回数据集合
	 * @return List<Map> list 如果有多表查询，则通过多个表别名与resultMap的映射关系(Map<Alias, Entity>)，获取多个表别名与对象实例映射的列表数据
	 * @return List<DataValue> list 如果只有单表查询，则直接查询出结果集
	 * @exception 若未设置表别名alias，则抛出SystemException
	 */
	public List list(){
		if (criteria==null){
			throw new SystemException("no enough information for query,can't do this");
		}
		criteria.setFacade(facade);
		
		if (criteria.isJoinQuery()) {
			Map<String, Class> classMap = new HashMap<String, Class>();
			String alias = criteria.getAlias();
			if (alias == null || "".equals(alias)) {
				throw new SystemException("Table " + criteria.getCriteriaContext().getTable() + " alias undefined.");
			}
			classMap.put(alias, criteria.getReturnType());
			List<JoinBridge> joinBridgeList = criteria.getJoinBridgeList();
			for (JoinBridge joinBridge : joinBridgeList) {
				Criteria rightCriteria =  joinBridge.getRightCriteria();
				rightCriteria.setFacade(facade);
				alias = rightCriteria.getAlias();
				if (alias == null || "".equals(alias)) {
					throw new SystemException("SQL From clause (" + rightCriteria.getCriteriaContext().getTable() + ") alias undefined.");
				}
				classMap.put(alias, rightCriteria.getReturnType());
			}
			return facade.query(classMap,criteria.toSqlString(),criteria.getParameters());
		} else {
			return ListEntity();
		}
	}
	/**
	 * 原数据查询方法，返回数据集合，无需强制设置表别名
	 * @return List<DataValue> list 无论单表还是夺标查询，直接查询出结果集
	 */
	public List ListEntity(){
		if (criteria==null){
			throw new SystemException("no enough information for query,can't do this");
		}
		criteria.setFacade(facade);
		
		return facade.query(criteria.getReturnType(),criteria.toSqlString(),criteria.getParameters());
	}
	public Object uniqueResult(){
//		if (criteria==null){
//			throw new SystemException("no enough information for query,can't do this");
//		}
//		getFacade();
//		criteria.setFacade(facade);
//		return facade.queryOne(criteria.getReturnType(),criteria.toSqlString(),criteria.getParameters());
		List list = this.list();
		if (list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	public Object uniqueResult(Class returnType){
		if (criteria==null){
			throw new SystemException("no enough information for query,can't do this");
		}
		criteria.setFacade(facade);
		return facade.queryOne(returnType,criteria.toSqlString(),criteria.getParameters());
	}
}
