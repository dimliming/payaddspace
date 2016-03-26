package com.payadd.framework.ddl.query.criterion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Junction implements Criterion {
	private List criterions = new ArrayList();
	private String op;
	
	protected Junction(String op){
		this.op = op;
	}
	public Junction addCriterion(Criterion c){
		criterions.add(c);
		return this;
	}
	public List getParameters(Criteria c) {
		List list = new ArrayList();
		int size = criterions.size();
		for (int i=0;i<size;i++){
			Criterion criterion = (Criterion)criterions.get(i);
			List params = criterion.getParameters(c);
			if (params!=null)list.addAll(params);
		}
		return list;
	}

	public String toSqlString(Criteria c) {
		if (criterions.size()==0)return "1=1";
		StringBuffer sql = new StringBuffer();
		sql.append("(");
		Iterator it = criterions.iterator();
		while(it.hasNext()){
			Criterion criterion = (Criterion)it.next();
			sql.append(criterion.toSqlString(c));
			if (it.hasNext())sql.append(" ").append(op).append(" ");
		}
		sql.append(")");
		return sql.toString();
	}

}
