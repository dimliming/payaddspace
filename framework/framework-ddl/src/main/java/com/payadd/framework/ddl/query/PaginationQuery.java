package com.payadd.framework.ddl.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.framework.ddl.query.criterion.Criteria;
import com.payadd.framework.ddl.query.criterion.JoinBridge;
import com.payadd.framework.ddl.query.criterion.Projection;
import com.payadd.framework.ddl.query.criterion.Projections;
import com.payadd.framework.common.exception.SystemException;



public class PaginationQuery extends SimpleQuery {
//	private long totalRecord=-1;
//	private int currentPage=1;
//	private int totalPage=-1;
//	private int pageSize=20;//默认每次查10条记录，可以在程序中自由设置
//	private boolean isPagination = true;
	private PaginationInfo pi = new PaginationInfo();
	
	public PaginationQuery(DatabaseFacade facade,Object condition){
		super(facade,condition);
	}
	public PaginationQuery(DatabaseFacade facade,Class type){
		super(facade,type);
	}
	public PaginationQuery(DatabaseFacade facade,String entityName){
		super(facade,entityName);
	}
	private void countRecord(){
		//获取符合条件的记录的数量
		criteria.disableOrder();//查询记录数时，不需要order by子句
		Projection projection = criteria.getProjection();//先保存原来的projection
		Criteria c = null;
		if (projection.isGrouped()||criteria.isJoinQuery()){
			c = new Criteria(criteria);
			c.setProjection(Projections.rowCount());
		}else{
			criteria.setProjection(Projections.rowCount());//设置查询记录数的projection
			c = criteria;
		}
		List list = this.getFacade().query(Integer.class, c);
		if (list.size()>0){
			Integer num = (Integer)list.get(0);
			pi.totalRecord = num.longValue();
		}
		pi.totalPage = (int)pi.totalRecord/pi.pageSize;
		if (pi.totalRecord==0)pi.totalPage = 0;
		else if (pi.totalRecord<=pi.pageSize)pi.totalPage = 1;
		else if ((pi.totalRecord%pi.pageSize)>0)pi.totalPage++;//有余数，页数加1
		
		criteria.setProjection(projection);//恢复原来的projection
		criteria.enableOrder();
		
	}
	
	/**
	 * 数据查询方法，返回数据集合
	 * @return List<Map> list 如果有多表查询，则通过多个表别名与resultMap的映射关系(Map<Alias, DataValue>)，获取多个表别名与对象实例映射的列表数据
	 * @return List<DataValue> list 如果只有单表查询，则直接查询出结果集
	 * @exception 若未设置表别名alias，则抛出SystemException
	 */
	public List list(){
		if (criteria==null){
			throw new SystemException("no enough information for query,can't do this");
		}
		DatabaseFacade facade = getFacade();
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
					throw new SystemException("Table " + rightCriteria.getCriteriaContext().getTable() + " alias undefined.");
				}
				classMap.put(alias, rightCriteria.getReturnType());
			}
			
			if (pi.isPagination){
				countRecord();
				if (pi.currentPage>pi.totalPage)pi.currentPage = pi.totalPage;
				if (pi.currentPage==0)pi.currentPage=1;
				return getFacade().query(classMap, criteria.toSqlString(), criteria.getParameters(), (pi.currentPage-1)*pi.pageSize, pi.pageSize);
			}else{
				this.pi.totalPage = -2;//不需要分页，不需要初始化这些值
				this.pi.totalRecord = -2;//不需要分页，不需要初始化这些值
				this.pi.currentPage = 1;
				return getFacade().query(classMap, criteria.toSqlString(), criteria.getParameters());
			}
		} else {
			return listEntity();
		}
	}
	/**
	 * 原数据查询方法，返回数据集合，无需强制设置表别名
	 * @return List<DataValue> list 无论单表还是夺标查询，直接查询出结果集
	 */
	public List listEntity(){
		if (pi.isPagination){
			countRecord();
			if (pi.currentPage>pi.totalPage)pi.currentPage = pi.totalPage;
			if (pi.currentPage==0)pi.currentPage=1;
			return getFacade().query(criteria, (pi.currentPage-1)*pi.pageSize, pi.pageSize);
		}else{
			this.pi.totalPage = -2;//不需要分页，不需要初始化这些值
			this.pi.totalRecord = -2;//不需要分页，不需要初始化这些值
			this.pi.currentPage = 1;
			return getFacade().query(criteria);
		}
	}
	public void increasePage(){
		pi.currentPage++;
	}
	public void decreasePage(){
		pi.currentPage--;
	}
	public List gotoPage(int page){
		pi.currentPage = page;
		return list();
	}
	public Object uniqueResult(){
		throw new SystemException("not available in this object");
	}
	public int getCurrentPage() {
		return pi.currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.pi.currentPage = currentPage;
	}
	public int getPageSize() {
		return pi.pageSize;
	}
	public void setPageSize(int pageSize) {
		if (pageSize==0)throw new SystemException("page size can't be zero!!!");
		this.pi.pageSize = pageSize;
	}
	public long getTotalRecord() {
		if (pi.totalRecord==-1)countRecord();//如果没有初始化过，那么先初始化
		return pi.totalRecord;
	}
	public int getTotalPage() {
		if (pi.totalPage==-1)countRecord();//如果没有初始化过，那么先初始化
		return pi.totalPage;
	}
	public void enablePagination(){
		this.pi.isPagination = true;
	}
	public void disablePagination(){
		this.pi.isPagination = false;
	}
	public boolean isPagination() {
		return pi.isPagination;
	}
	public PaginationInfo getPaginationInfo(){
		return pi;
	}
}
