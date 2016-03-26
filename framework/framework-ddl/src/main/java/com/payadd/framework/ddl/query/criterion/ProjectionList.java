package com.payadd.framework.ddl.query.criterion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ProjectionList implements Projection {
	private List projectionList = new ArrayList();
	private boolean hasAggregate = false;
	private int priority;
	private Criteria belongToCriteria;
	
	public ProjectionList(){
		this.priority = -1;
	}
	public ProjectionList(int priority){
		this.priority = priority;
	}
	public ProjectionList add(Projection projection){
		if (projection instanceof AggregateProjection)hasAggregate = true;
		
		projectionList.add(projection);
		return this;
	}
	public String[] getColumnAliases() {
		return null;
	}

	public boolean isGrouped() {
		for (int i=0;i<projectionList.size();i++){
			Projection p = (Projection)projectionList.get(i);
			if (p.isGrouped())return true;
		}
		return false;
	}

	public String toGroupSqlString(Criteria c) {
		StringBuffer sql = new StringBuffer();
		Iterator it = projectionList.iterator();
		boolean hasPrevious = false;
		while(it.hasNext()){
			Projection projection = (Projection)it.next();
			String temp = projection.toGroupSqlString(c);
			if (temp==null)continue;
			
			if (hasPrevious)sql.append(",");
			sql.append(temp);
			hasPrevious=true;
		}
		return sql.toString();
	}
	public String toSqlString(Criteria c) {
		StringBuffer sql = new StringBuffer();
		Iterator it = projectionList.iterator();
		while(it.hasNext()){
			Projection projection = (Projection)it.next();
			sql.append(projection.toSqlString(c));
			if (it.hasNext())sql.append(",");
		}
		return sql.toString();
	}
	public List getGroupProjectionList(){
		List groupProjectionList = new ArrayList();
		for (int i=0;i<projectionList.size();i++){
			Projection p = (Projection)projectionList.get(i);
			if (p.isGrouped()){
				groupProjectionList.add(p);
			}
		}
		return groupProjectionList;
	}
	public boolean hasAggregate(){
		return hasAggregate;
	}
	public int getPriority() {
		return priority;
	}
	public void setBelongToCriteria(Criteria belongToCriteria) {
		this.belongToCriteria = belongToCriteria;
	}
}
