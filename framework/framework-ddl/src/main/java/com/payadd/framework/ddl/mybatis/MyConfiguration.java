package com.payadd.framework.ddl.mybatis;

import java.util.Iterator;
import java.util.Map;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.NestedResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.payadd.framework.ddl.executor.resultset.MyFastResultSetHandler;

public class MyConfiguration extends Configuration {
	private Map<String, EntityMetadata> entityMap;
	private String platformConfigPath;

	public String getPlatformConfigPath() {
		return platformConfigPath;
	}

	public void setPlatformConfigPath(String platformConfigPath) {
		this.platformConfigPath = platformConfigPath;
	}

	public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
		ParameterHandler parameterHandler = new ListParameterHandler(mappedStatement, parameterObject, boundSql);
		parameterHandler = (ParameterHandler) interceptorChain.pluginAll(parameterHandler);
		return parameterHandler;
	}

	@Override
	public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler,
			ResultHandler resultHandler, BoundSql boundSql) {
		ResultSetHandler resultSetHandler = mappedStatement.hasNestedResultMaps() ? new NestedResultSetHandler(executor, mappedStatement, parameterHandler,
				resultHandler, boundSql, rowBounds) : new MyFastResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
		resultSetHandler = (ResultSetHandler) interceptorChain.pluginAll(resultSetHandler);
		return resultSetHandler;
	}

	public Map<String, EntityMetadata> getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(Map<String, EntityMetadata> entityMap) {
		this.entityMap = entityMap;
		Iterator<EntityMetadata> it = entityMap.values().iterator();
		while (it.hasNext()) {
			EntityMetadata em = it.next();
			em.setConfiguration(this);
		}
	}
}
