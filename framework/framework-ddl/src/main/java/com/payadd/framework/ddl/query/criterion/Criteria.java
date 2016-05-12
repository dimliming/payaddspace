package com.payadd.framework.ddl.query.criterion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.framework.ddl.mybatis.EntityMetadata;
import com.payadd.framework.ddl.mybatis.IbatisContext;
import com.payadd.framework.common.exception.SystemException;



public class Criteria implements java.io.Serializable{
	public static final PriorityComparator priComparator = new PriorityComparator();
	
	private String entityName;
	private Criteria fromCriteria;
	private String alias;
	private boolean isFirstAccess = true;
	
	private List criterionList;
	private List orderList;
	private Projection projection;

	private List<JoinBridge> joinBridgeList;//连接查询
	
	
	
//	private PropertyMap resultMap;
//	private EntityMetadata entityMetadata;
//	private IbatisExecutorDelegate delegate;
//	private BeanEntityTache tache;
	private CriteriaContext context;
	private DatabaseFacade facade;
	
	private String prefix;
	private boolean enableOrder = true;
	
	public Criteria(Class type){
		this(type.getName(),null);
	}
	public Criteria(Class type,String alias){
		this(type.getName(),alias);
	}
	public Criteria(String entityName){
		this(entityName,null);
	}
	public Criteria(String entityName,String alias){
		this.entityName = entityName;
		this.criterionList = new ArrayList();
		this.orderList = new ArrayList();
		this.alias = alias;
		
		if (this.alias==null)this.alias = defaultAlias();
		
		this.projection = Projections.allProjection();
		
		if (this.alias!=null)prefix = this.alias+".";
		else prefix = "";
	}
	public Criteria(Criteria fromCriteria,String alias){
		this.fromCriteria = fromCriteria;
		this.criterionList = new ArrayList();
		this.orderList = new ArrayList();
		this.alias = alias;
		this.entityName=fromCriteria.getEntityName();
		prefix = "";
	}
	public Criteria(Criteria fromCriteria){
		this(fromCriteria,null);
	}
//	public static Criteria dualCriteria(){
//		//Class type = null;
//		String entityName = null;
//		String alias = null;
//		return new Criteria(entityName,alias){
//			public String toTableSql(){
//				return "dual";
//			}
//		};
//		
//	}
	private String defaultAlias(){
		/*if (entityName==null)return null;
		String underlineEntityName = entityName.replaceAll("\\.", "_")+"__";
		return underlineEntityName;*/
		return null;
	}
	public void as(String alias){
		this.alias = alias;
		if (this.alias!=null)prefix = this.alias+".";
	}
	public Projection getProjection() {
		return projection;
	}
	public void setProjection(Projection projection){
		this.projection = projection;
	}
	public Criteria addOrder(Order order){
		orderList.add(order);
		return this;
	}
	public Criteria add(Criterion criterion){
		criterionList.add(criterion);
		return this;
	}
	public String toProjectionSql(){
		StringBuffer sql = new StringBuffer();
		if (projection!=null)sql.append(projection.toSqlString(this));
		if (joinBridgeList!=null){
			for (int i=0,size=joinBridgeList.size();i<size;i++){
				JoinBridge c = (JoinBridge)joinBridgeList.get(i);
				c.getRightCriteria().setFacade(facade);
				String temp = c.getRightCriteria().toProjectionSql();
				if (temp!=null&&!"".equals(temp)){
					if (sql.length()>0)sql.append(",").append(temp);
					else sql.append(temp);
				}
			}
		}
		return sql.toString();
	}
	public String toTableSql(){
		StringBuffer sql = new StringBuffer();
		if (fromCriteria!=null){
			if (this.facade!=null)
			{
				fromCriteria.setFacade(facade);
			}
//			if (tache!=null)
//			{
//				fromCriteria.setTache(this.tache);
//			}
			sql.append("(").append(fromCriteria.toSqlString()).append(")");
			if (this.alias != null && !"".equals(this.alias)) {
				sql.append(" ").append(this.alias);
			} else {
				sql.append(" ").append(context.getTable()); // default alias
			}
		}else{
			sql.append(context.getTable());
		}
		if (alias!=null)sql.append(" ").append(alias);
		if (joinBridgeList!=null){
			for (int i=0,size=joinBridgeList.size();i<size;i++){
				JoinBridge j = (JoinBridge)joinBridgeList.get(i);
				sql.append(j.toSqlString());
			}
		}
		return sql.toString();
	}
	public String toWhereSql(){
		StringBuffer sql = new StringBuffer();
		if (criterionList.size()>0){
			Iterator it = criterionList.iterator();
			while(it.hasNext()){
				Criterion c = (Criterion)it.next();
				sql.append(c.toSqlString(this));
				if (it.hasNext())sql.append(" and ");
			}
		}
		if (joinBridgeList!=null){
			for (int i=0,size=joinBridgeList.size();i<size;i++){
				JoinBridge j = (JoinBridge)joinBridgeList.get(i);
				String joinWhereSql = j.getRightCriteria().toWhereSql();
				if (joinWhereSql!=null&&joinWhereSql.length()>0){
					if (sql.length()>0)sql.append(" and ").append(joinWhereSql);
					else sql.append(j.getRightCriteria().toWhereSql());
				}
			}
		}
		return sql.toString();
	}
	private List getAllOrderList(){
		List allOrderList = new ArrayList();
		for (int i=0;i<orderList.size();i++){
			((Order)orderList.get(i)).setBelongToCriteria(this);
		}
		allOrderList.addAll(orderList);
		if (joinBridgeList!=null){
			for (int i=0,size=joinBridgeList.size();i<size;i++){
				JoinBridge j = (JoinBridge)joinBridgeList.get(i);
				allOrderList.addAll(j.getRightCriteria().getAllOrderList());
			}
		}
		return allOrderList;
	}
	public String toOrderSql(){
		StringBuffer sql = new StringBuffer();
		
		/*if (orderList.size()>0){
			Iterator it = orderList.iterator();
			while(it.hasNext()){
				Order o = (Order)it.next();
				sql.append(o.toSqlString(this));
				if (it.hasNext())sql.append(",");
			}
		}
		if (joinBridgeList!=null){
			for (int i=0,size=joinBridgeList.size();i<size;i++){
				JoinBridge j = (JoinBridge)joinBridgeList.get(i);
				String joinOrderSql = j.getRightCriteria().toOrderSql();
				if (joinOrderSql!=null&&!"".equals(joinOrderSql.trim())){
					if (sql.length()>0)sql.append(",").append(joinOrderSql);
					else sql.append(joinOrderSql);
				}
			}
		}*/
		List allOrderList = getAllOrderList();
		//sort
		Collections.sort(allOrderList, priComparator);
		for (int i=0;i<allOrderList.size();i++){
			Order order = (Order)allOrderList.get(i);
			if (i!=0) sql.append(",");
			sql.append(order.toSqlString());
		}
		
		return sql.toString();
	}
	public List getAllGroupProjection(){
		List groupProjectionList = new ArrayList();
		if (projection!=null){
			if (projection instanceof ProjectionList){
				List tempList = ((ProjectionList)projection).getGroupProjectionList();
				for (int i=0;i<tempList.size();i++){
					groupProjectionList.add(new GroupProjection((Projection)tempList.get(i),this));
				}
			}else if (projection.isGrouped()){
				groupProjectionList.add(new GroupProjection(projection,this));
			}
		}
		if (joinBridgeList!=null){
			for (int i=0,size=joinBridgeList.size();i<size;i++){
				JoinBridge j = (JoinBridge)joinBridgeList.get(i);
				groupProjectionList.addAll(j.getRightCriteria().getAllGroupProjection());
			}
		}
		return groupProjectionList;
	}
	public String toGroupSql(){
		StringBuffer sql = new StringBuffer();
		/*if (projection!=null&&projection.isGrouped())sql.append(projection.toGroupSqlString(this));
		if (joinBridgeList!=null){
			for (int i=0,size=joinBridgeList.size();i<size;i++){
				JoinBridge c = (JoinBridge)joinBridgeList.get(i);
				String temp = c.getRightCriteria().toGroupSql();
				if (temp!=null&&!"".equals(temp)){
					if (sql.length()>0)sql.append(",").append(temp);
					else sql.append(temp);
				}
			}
		}*/
		List allGroupList = getAllGroupProjection();
		Collections.sort(allGroupList, priComparator);
		for (int i=0;i<allGroupList.size();i++){
			GroupProjection group = (GroupProjection)allGroupList.get(i);
			if (i!=0) sql.append(",");
			sql.append(group.getProjection().toSqlString(group.getCriteria()));
		}
		return sql.toString();
	}
	public String toSqlString(){
		getEntityMetadata();
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(toProjectionSql());
		
		sql.append(" from ");
		sql.append(toTableSql());
		
		String temp = toWhereSql();
		if (temp!=null&&!"".equals(temp)){
			sql.append(" where ").append(temp);
		}

		temp = toGroupSql();
		if (temp!=null&&!"".equals(temp)){
			sql.append(" group by ").append(temp);
		}
		
		if (enableOrder){//如果没有禁用order，那么生成order by子句(在)
			temp = toOrderSql();
			if (temp!=null&&!"".equals(temp)){
				sql.append(" order by ").append(temp);
			}
		}
		return sql.toString();
	}
	public List getParameters(){
		List parameters = new ArrayList();
		List temp = null;
		if (fromCriteria!=null){
			temp = fromCriteria.getParameters();
			if (temp!=null){
				parameters.addAll(temp);
			}
		}
		Iterator it = criterionList.iterator();
		while(it.hasNext()){
			Criterion c = (Criterion)it.next();
			temp = c.getParameters(this);
			if (temp!=null)
				parameters.addAll(temp);
		}
		if (joinBridgeList!=null){
			for (int i=0,size=joinBridgeList.size();i<size;i++){
				JoinBridge j = (JoinBridge)joinBridgeList.get(i);
				parameters.addAll(j.getRightCriteria().getParameters());
			}
		}
		
		return parameters;
	}
	public Criteria leftjoin(Criteria c,String[] leftProperties,String[] rightProperties){
		return join("left join",c,leftProperties,rightProperties);
	}
	public Criteria rightjoin(Criteria c,String[] leftProperties,String[] rightProperties){
		return join("right join",c,leftProperties,rightProperties);
	}
	public Criteria innerjoin(Criteria c,String[] leftProperties,String[] rightProperties){
		return join("inner join",c,leftProperties,rightProperties);
	}
	public Criteria fulljoin(Criteria c,String[] leftProperties,String[] rightProperties){
		return join("full join",c,leftProperties,rightProperties);
	}
	public Criteria join(String joinType,Criteria c,String[] leftProperties,String[] rightProperties){
		JoinBridge j = new JoinBridge(this,joinType,c);
		for (int i=0;i<leftProperties.length;i++){
			j.addConnector(leftProperties[i], rightProperties[i]);
		}
		if (joinBridgeList==null)joinBridgeList = new ArrayList();
		joinBridgeList.add(j);
		return this;
	}
	public boolean isJoinQuery(){//是否联合查询
		return joinBridgeList!=null&&joinBridgeList.size()>0;
	}
	public void disableOrder(){
		this.enableOrder = false;
	}
	public void enableOrder(){
		this.enableOrder = true;
	}
//	public void setDelegate(IbatisExecutorDelegate delegate){
//		this.delegate = delegate;
//		context = new IbatisContext(getEntityMetadata());
//	}
//	public IbatisExecutorDelegate getDelegate(){
//		return this.delegate;
//	}
//	public void setTache(BeanEntityTache tache){
//		this.tache = tache;
//		context = new JdbcContext(tache);
//	}
	public CriteriaContext getCriteriaContext(){
		return context;
	}
	public String getColumnName(String propertyName){
		getEntityMetadata();
		//Property prop = entityMetadata.getProperty(propertyName);
		String col = context.getColumnName(propertyName);
		if (col==null)throw new SystemException("there is no property '"+propertyName+"' in entity '"+entityName+"'");
		return prefix+col;//返回加上了前缀（即别名）的字段名
	}
	private EntityMetadata getEntityMetadata(){
//		if (entityMetadata==null){
//			entityMetadata = delegate.getMappingContext().getEntityMapping(this.entityName);
//		}
//		return entityMetadata;
		//return delegate.getMappingContext().getEntityMapping(this.entityName);
		return this.facade.getEntityMetadata(this.entityName);
	}
//	public PropertyMap getPropertyMap(){
//		getEntityMetadata();
//		return entityMetadata.getPropertyMap();
//	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getPrefix() {
		return prefix;
	}
	public List<JoinBridge> getJoinBridgeList() {
		return joinBridgeList;
	}
	public Class getReturnType(){
//		getEntityMetadata();
//		return entityMetadata.getType();
		return context.getReturnType();
	}
	public boolean isFirstAccess() {
		return isFirstAccess;
	}
	public void setFirstAccess(boolean isFirstAccess) {
		this.isFirstAccess = isFirstAccess;
	}
	public DatabaseFacade getFacade() {
		return facade;
	}
	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
		EntityMetadata em = facade.getEntityMetadata(this.entityName);
		this.context = new IbatisContext(em);
	}
	
}
class GroupProjection implements Priority{
	private Projection projection;
	private Criteria criteria;
	
	public GroupProjection(Projection p,Criteria c){
		this.projection = p;
		this.criteria = c;
	}
	public Projection getProjection() {
		return projection;
	}
	public void setProjection(Projection projection) {
		this.projection = projection;
	}
	public Criteria getCriteria() {
		return criteria;
	}
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	public int getPriority() {
		return projection.getPriority();
	}
	
}
