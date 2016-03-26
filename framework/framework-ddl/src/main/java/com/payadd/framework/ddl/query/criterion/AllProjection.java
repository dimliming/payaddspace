package com.payadd.framework.ddl.query.criterion;

import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;

import com.payadd.framework.ddl.mybatis.EntityMetadata;
import com.payadd.framework.common.exception.SystemException;

public class AllProjection implements Projection {

	private static final long serialVersionUID = 8451611517475425055L;
	
	public String[] getColumnAliases() {
		return null;
	}

	public boolean isGrouped() {
		return false;
	}

	public String toGroupSqlString(Criteria c) {
		return null;
	}

	public String toSqlString(Criteria c) {
		StringBuffer sql = new StringBuffer();
		EntityMetadata entityMetadata = c.getFacade().getEntityMetadata(c.getEntityName());
		if (entityMetadata == null) {
			throw new SystemException("EntityName: " + c.getEntityName() + " does not exist.");
		}
		List<ResultMapping> resultMappingList = entityMetadata.getResultMap().getResultMappings();
		if (resultMappingList == null) {
			throw new SystemException("resultMappingList is empty."); //TODO
		}
		final String alias = c.getAlias();
		List<JoinBridge> joinBridgeList = c.getJoinBridgeList();
		if (alias == null || "".equals(alias)) {
			sql.append(" ").append(c.getPrefix()).append("*").append(" ");
		} else {
			String labelName = "__" + alias + "__";
			for (ResultMapping resultMapping : resultMappingList) {
				String columnName = resultMapping.getColumn();
				sql.append(" ").append(c.getPrefix()).append(columnName).append(" ").append(labelName).append(columnName).append(",");
			}
			sql.deleteCharAt(sql.length() - 1).append(" ");
		}
		
		return sql.toString();
	}
	public int getPriority() {
		return -1;
	}
}
