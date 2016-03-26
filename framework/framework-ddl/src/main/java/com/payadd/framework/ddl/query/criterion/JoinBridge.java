package com.payadd.framework.ddl.query.criterion;

import java.util.ArrayList;
import java.util.List;

public class JoinBridge {
	private List leftProperties;
	private List rightProperties;
	private final String joinType;
	private final Criteria leftCriteria;
	private final Criteria rightCriteria;
	
	public JoinBridge(Criteria leftCriteria,String joinType,Criteria rightCriteria){
		this.leftCriteria = leftCriteria;
		this.joinType = joinType;
		this.rightCriteria = rightCriteria;
	}
	public void addConnector(String leftProperty,String rightProperty){
		if (leftProperties==null){
			leftProperties = new ArrayList();
			rightProperties = new ArrayList();
		}
		leftProperties.add(new PropertyProjection(leftProperty));
		rightProperties.add(new PropertyProjection(rightProperty));
	}
	
	public String toSqlString(){
		StringBuffer sql = new StringBuffer();
		sql.append(" ").append(joinType).append(" ");
		sql.append(rightCriteria.toTableSql());
		if (leftProperties!=null&&leftProperties.size()>0){
			sql.append(" on ");
			for (int i=0;i<leftProperties.size();i++){
				PropertyProjection leftP = (PropertyProjection)leftProperties.get(i);
				PropertyProjection rightP = (PropertyProjection)rightProperties.get(i);
				if (i==0){
					sql.append(leftP.toSqlString(leftCriteria)).append("=").append(rightP.toSqlString(rightCriteria));
				}else{
					sql.append(" and ").append(leftP.toSqlString(leftCriteria)).append("=").append(rightP.toSqlString(rightCriteria));
				}
			}
		}
		
		return sql.toString();
	}
	public Criteria getLeftCriteria() {
		return leftCriteria;
	}
	public Criteria getRightCriteria() {
		return rightCriteria;
	}

}
