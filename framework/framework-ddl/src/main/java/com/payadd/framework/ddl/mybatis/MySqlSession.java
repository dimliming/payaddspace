package com.payadd.framework.ddl.mybatis;

import java.util.List;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

public class MySqlSession extends DefaultSqlSession implements SqlSessionExtend {
	private Executor executor;

	public MySqlSession(Configuration configuration, Executor executor, boolean autoCommit) {
		super(configuration, executor, autoCommit);
		this.executor = executor;
	}

	public List selectList(MappedStatement ms, Object parameter) {
		try {
			return executor.query(ms, parameter, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error querying database.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}

	public int update(MappedStatement ms, Object parameter) {
		try {
			return executor.update(ms, parameter);
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error updating database.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}

	public List selectList(MappedStatement ms, Object parameter, ResultHandler handler) {
		try {
			return executor.query(ms, parameter, RowBounds.DEFAULT, handler);
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error querying database.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}

}
