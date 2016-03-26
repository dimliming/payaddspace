package com.payadd.framework.ddl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.payadd.framework.ddl.mybatis.EntityMetadata;
import com.payadd.framework.ddl.query.criterion.Criteria;
import com.payadd.framework.common.exception.SystemException;

public interface DatabaseFacade {
	Object selectOne(String statement);

	Object selectOne(String statement, Object parameter);

	List selectList(String statement);

	List selectList(String statement, Object parameter);

	List selectList(String statement, Object parameter, RowBounds rowBounds);

	Map selectMap(String statement, String mapKey);

	Map selectMap(String statement, Object parameter, String mapKey);

	Map selectMap(String statement, Object parameter, String mapKey,RowBounds rowBounds);

	void select(String statement, Object parameter, ResultHandler handler);

	void select(String statement, ResultHandler handler);

	void select(String statement, Object parameter, RowBounds rowBounds,
			ResultHandler handler);

	int insert(String statement);

	int insert(String statement, Object parameter);

	int update(String statement);

	int update(String statement, Object parameter);

	int delete(String statement);

	int delete(String statement, Object parameter);

	void commit();

	void commit(boolean force);

	void rollback();

	void rollback(boolean force);

	public List<BatchResult> flushStatements();

	void close();

	void clearCache();

	Configuration getConfiguration();

	<T> T getMapper(Class<T> type);

	Connection getConnection();
	
	////////////////////////////////////////////////
	
	public int delete(Class type, Object id) throws SystemException;

	public int delete(Class type, String[] props, Object[] values) throws SystemException;

	public int delete(Object entity) throws SystemException;

	public int delete(String entityName, String[] props, Object[] values) throws SystemException;
	
	public int deleteByEntityName(String entityName, Object id) throws SystemException;
	
	public int executeUpdate(String sql, List parameters) throws SystemException;
	
	public int executeProcedure(String procedureName, ProcedureParameter pp) throws SystemException;
	
	public int insertByEntityName(String entityName,Object entity) throws SystemException;

	public int insert(Object entity) throws SystemException;

	public Object load(Class type, Object id) throws SystemException;

	public Object load(String entityName, Object id) throws SystemException;

//	public Object load(String entityName, String[] props, Object[] values) throws SystemException;
	
	public List query(Class type, String sql, List parameter, int skip, int max) throws SystemException;

	public List query(Class type, String sql, List parameter) throws SystemException;

	public List query(Criteria c, int skip, int max) throws SystemException;
//	
	public List query(Criteria c) throws SystemException;
	
	public List query(Class type,Criteria c) throws SystemException;

	public List query(String sql, List parameter, int skip, int max) throws SystemException;

	public List query(String sql, List parameter) throws SystemException;

	public List query(String entityName, String sql, List parameter, int skip, int max) throws SystemException;

	public List query(String entityName, String sql, List parameter) throws SystemException;
	
//	public Object queryOne(Class type, Criteria c) throws SystemException;

	public Object queryOne(Class type, String sql, List parameter) throws SystemException;

	public Object queryOne(String sql, List parameter) throws SystemException;

	public Object queryOne(String entityName, String sql, List parameter) throws SystemException;

	public int updateByEntityName(String entityName,Object entity) throws SystemException;

	public int update(Object entity) throws SystemException;

	public EntityMetadata getEntityMetadata(String entityName);
//	public int executeUpdateProcedure(String procedureName, List parameters) throws SystemException ;
//	public int executeUpdateProcedure(String procedureName, List parameters,ProcedureParameterContext parameterContext) throws SystemException; 

	public List query(Map<String, Class> classMap, String sql, List parameter) throws SystemException;

	public List query(Map<String, Class> classMap, String sql, List parameter, int skip, int max) throws SystemException;
}
