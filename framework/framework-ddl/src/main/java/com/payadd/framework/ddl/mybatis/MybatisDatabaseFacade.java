package com.payadd.framework.ddl.mybatis;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;

import com.payadd.framework.ddl.DatabaseFacade;
import com.payadd.framework.ddl.ProcedureParameter;
import com.payadd.framework.ddl.executor.result.MyResultHandler;
import com.payadd.framework.ddl.query.criterion.Criteria;
import com.payadd.framework.common.exception.SystemException;

public class MybatisDatabaseFacade implements DatabaseFacade {
	private SqlSessionFactory sqlSessionFactory;
	private Configuration configuration;
	private MySqlSessionTemplate sqlSession;

	public Object selectOne(String statement) {
		return this.openSession().selectOne(statement);
	}

	public Object selectOne(String statement, Object parameter) {
		return this.openSession().selectOne(statement, parameter);
	}

	public List selectList(String statement) {
		return this.openSession().selectList(statement);
	}

	public List selectList(String statement, Object parameter) {
		return this.openSession().selectList(statement, parameter);
	}

	public List selectList(String statement, Object parameter, RowBounds rowBounds) {
		return this.openSession().selectList(statement, parameter, rowBounds);
	}

	public Map selectMap(String statement, String mapKey) {
		return this.openSession().selectMap(statement, mapKey);
	}

	public Map selectMap(String statement, Object parameter, String mapKey) {
		return this.openSession().selectMap(statement, parameter, mapKey);
	}

	public Map selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		return this.openSession().selectMap(statement, parameter, mapKey, rowBounds);
	}

	public void select(String statement, Object parameter, ResultHandler handler) {
		this.openSession().select(statement, parameter, handler);

	}

	public void select(String statement, ResultHandler handler) {
		this.openSession().select(statement, handler);
	}

	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		this.openSession().select(statement, parameter, rowBounds, handler);
	}

	public int insert(String statement) {
		return this.openSession().insert(statement);
	}

	public int insert(String statement, Object parameter) {
		return this.openSession().insert(statement, parameter);
	}

	public int update(String statement) {
		return this.openSession().update(statement);
	}

	public int update(String statement, Object parameter) {
		return this.openSession().update(statement, parameter);
	}

	public int delete(String statement) {
		return this.openSession().delete(statement);
	}

	public int delete(String statement, Object parameter) {
		return this.openSession().delete(statement, parameter);
	}

	public void commit() {
		this.openSession().commit();
	}

	public void commit(boolean force) {
		this.openSession().commit(force);
	}

	public void rollback() {
		this.openSession().rollback();
	}

	public void rollback(boolean force) {
		this.openSession().rollback(force);
	}

	public List<BatchResult> flushStatements() {
		return this.openSession().flushStatements();
	}

	public void close() {
		this.openSession().close();
	}

	public void clearCache() {
		this.openSession().clearCache();
	}

	public Configuration getConfiguration() {
		return this.openSession().getConfiguration();
	}

	public <T> T getMapper(Class<T> type) {
		return this.openSession().getMapper(type);
	}

	public Connection getConnection() {
		return this.openSession().getConnection();
	}

	public int delete(Class type, Object id) throws SystemException {
		MappedStatementBuilder builder = new DeleteMappedStatementBuilder(configuration, type, id);
		return this.openSession().update(builder.build(), id);
	}

	public int delete(Class type, String[] props, Object[] values) throws SystemException {
		// TODO Auto-generated method stub
		throw new SystemException("not implement yet!");
	}

	public int delete(Object entity) throws SystemException {
		MappedStatementBuilder builder = new DeleteMappedStatementBuilder(configuration, entity.getClass(), entity);
		return this.openSession().update(builder.build(), entity);
	}

	public int delete(String entityName, String[] props, Object[] values) throws SystemException {
		// TODO Auto-generated method stub
		throw new SystemException("not implement yet!");
	}

	public int deleteByEntityName(String entityName, Object id) throws SystemException {
		MappedStatementBuilder builder = new DeleteMappedStatementBuilder(configuration, entityName, id);
		return this.openSession().update(builder.build(), id);
	}

	public int executeUpdate(String sql, List parameters) throws SystemException {
		SqlUpdateMappedStatementBuilder builder = new SqlUpdateMappedStatementBuilder(configuration, sql, parameters);
		return this.openSession().update(builder.build(), builder.getParameterObject());
	}

	public int insertByEntityName(String entityName, Object entity) throws SystemException {
		MappedStatementBuilder builder = new CreateMappedStatementBuilder(configuration, entityName, entity);
		return this.openSession().update(builder.build(), entity);
	}

	public int insert(Object entity) throws SystemException {
		MappedStatementBuilder builder = new CreateMappedStatementBuilder(configuration, entity.getClass(), entity);
		// MySqlSession session = this.openSession();
		// int row = session.update(builder.build(), entity);
		// session.close();
		int row = this.openSession().update(builder.build(), entity);
		return row;
	}

	public Object load(Class type, Object id) throws SystemException {
		QueryMappedStatementBuilder builder = new QueryMappedStatementBuilder(configuration, type, id);
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public Object load(String entityName, Object id) throws SystemException {
		QueryMappedStatementBuilder builder = new QueryMappedStatementBuilder(configuration, entityName, id);
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List query(Class type, String sql, List parameter, int skip, int max) throws SystemException {
		printSql(sql,parameter);
		QueryMappedStatementBuilder builder = new QueryMappedStatementBuilder(configuration, type, sql, parameter, skip, max);
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject());
		return list;
	}

	public List query(Class type, String sql, List parameter) throws SystemException {
		printSql(sql,parameter);
		QueryMappedStatementBuilder builder = new QueryMappedStatementBuilder(configuration, type, sql, parameter, -1, -1);
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject());
		return list;
	}
	
	/**
	 * 查询数据列表
	 * @param metaMap 表别名与resultMap的映射关系
	 * @param sql 查询SQL
	 * @param parameter 查询参数
	 * @return List<Map> list 如果有多表查询，则通过多个表别名与resultMap的映射关系(Map<Alias, ResultMap>)，获取别名与对象实例映射的列表数据
	 */
	public List query(Map<String, Class> classMap, String sql, List parameter) throws SystemException {
		printSql(sql,parameter);
		if (classMap == null || classMap.isEmpty()) return new ArrayList();
		
		QueryMappedStatementBuilder builder = null;
		Map<String, ResultMap> metaMap = new HashMap<String, ResultMap>();
		// 获取到handler对象metaMap所需要的映射关系
		for (String aliasName : classMap.keySet()) {
			Class type = classMap.get(aliasName);
			if (type == null) {
				throw new SystemException("Result class for [" + aliasName + "] is undefined.");
			}
			EntityMetadata em = ((MyConfiguration)this.configuration).getEntityMap().get(type.getName());
			if (em != null) {
				ResultMap resultMap = configuration.getResultMap(em.getMapId());
				metaMap.put(aliasName, resultMap);
			} else {
				ResultMap.Builder rmBuilder = new ResultMap.Builder(configuration, "", type, new ArrayList<ResultMapping>());
				metaMap.put(aliasName, rmBuilder.build());
			}
			
			if (builder == null) {
				builder = new QueryMappedStatementBuilder(configuration, type, sql, parameter, -1, -1);
			}
		}
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject(), new MyResultHandler(metaMap));

		return list;
	}
	
	/**
	 * 查询数据列表
	 * @param metaMap 表别名与resultMap的映射关系
	 * @param sql 查询SQL
	 * @param parameter 查询参数
	 * @param skip 分页参数
	 * @param max 分页参数
	 * @return List<Map> list 如果有多表查询，则通过多个表别名与resultMap的映射关系(Map<Alias, ResultMap>)，或者字段名与ReturnType之间的映射关系(Map<Alias, Class>)，获取别名与对象实例映射的列表数据
	 */
	public List query(Map<String, Class> classMap, String sql, List parameter, int skip, int max) throws SystemException {
		printSql(sql,parameter);
		if (classMap == null || classMap.isEmpty()) return new ArrayList();
		
		QueryMappedStatementBuilder builder = null;
		Map<String, ResultMap> metaMap = new HashMap<String, ResultMap>();
		// 获取到handler对象metaMap所需要的映射关系
		for (String aliasName : classMap.keySet()) {
			Class type = classMap.get(aliasName);
			if (type == null) {
				throw new SystemException("Result class for [" + aliasName + "] is undefined.");
			}
			EntityMetadata em = ((MyConfiguration)this.configuration).getEntityMap().get(type.getName());
			if (em != null) {
				ResultMap resultMap = configuration.getResultMap(em.getMapId());
				metaMap.put(aliasName, resultMap);
			} else {
				ResultMap.Builder rmBuilder = new ResultMap.Builder(configuration, "", type, new ArrayList<ResultMapping>());
				metaMap.put(aliasName, rmBuilder.build());
			}
			
			if (builder == null) {
				builder = new QueryMappedStatementBuilder(configuration, type, sql, parameter, skip, max);
			}
		}
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject(), new MyResultHandler(metaMap));

		return list;
	}

	public List query(String sql, List parameter, int skip, int max) throws SystemException {
		printSql(sql,parameter);
		QueryMappedStatementBuilder builder = new QueryMappedStatementBuilder(configuration, sql, parameter, skip, max);
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject());
		return list;
	}

	public List query(String sql, List parameter) throws SystemException {
		printSql(sql,parameter);
		QueryMappedStatementBuilder builder = new QueryMappedStatementBuilder(configuration, sql, parameter, -1, -1);
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject());
		return list;
	}
	private void printSql(String sql,List parameter){
		System.out.println("sql="+sql);
		if (parameter==null)return;
		StringBuffer sb = new StringBuffer();
		sb.append("parameter=[");
		if (parameter.size()>0)sb.append(parameter.get(0).toString());
		for (int i=1;i<parameter.size();i++){
			sb.append(",").append(parameter.get(i));
		}
		sb.append("]");
		System.out.println(sb.toString());
	}
	public List query(String entityName, String sql, List parameter, int skip, int max) throws SystemException {
		printSql(sql,parameter);
		QueryMappedStatementBuilder builder = new QueryMappedStatementBuilder(configuration, entityName, sql, parameter, skip, max);
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject());
		return list;
	}

	public List query(String entityName, String sql, List parameter) throws SystemException {
		printSql(sql,parameter);
		QueryMappedStatementBuilder builder = new QueryMappedStatementBuilder(configuration, entityName, sql, parameter, -1, -1);
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject());
		return list;
	}

	public Object queryOne(Class type, String sql, List parameter) throws SystemException {
		printSql(sql,parameter);
		QueryMappedStatementBuilder builder = new QueryMappedStatementBuilder(configuration, type, sql, parameter, -1, -1);
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public Object queryOne(String sql, List parameter) throws SystemException {
		printSql(sql,parameter);
		QueryMappedStatementBuilder builder = new QueryMappedStatementBuilder(configuration, sql, parameter, -1, -1);
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public Object queryOne(String entityName, String sql, List parameter) throws SystemException {
		printSql(sql,parameter);
		QueryMappedStatementBuilder builder = new QueryMappedStatementBuilder(configuration, entityName, sql, parameter, -1, -1);
		List list = this.openSession().selectList(builder.build(), builder.getParameterObject());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public int updateByEntityName(String entityName, Object entity) throws SystemException {
		MappedStatementBuilder builder = new UpdateMappedStatementBuilder(configuration, entityName, entity);
		return this.openSession().update(builder.build(), entity);
	}

	public int update(Object entity) throws SystemException {
		MappedStatementBuilder builder = new UpdateMappedStatementBuilder(configuration, entity.getClass(), entity);
		return this.openSession().update(builder.build(), entity);
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.configuration = sqlSessionFactory.getConfiguration();
		this.sqlSession = new MySqlSessionTemplate(sqlSessionFactory);
	}

	public MySqlSessionTemplate openSession() {
		// return (MySqlSession)this.sqlSessionFactory.openSession();
		return this.sqlSession;
	}

	public EntityMetadata getEntityMetadata(String entityName) {
		EntityMetadata md = ((MyConfiguration) configuration).getEntityMap().get(entityName);
		if (md==null){
			throw new SystemException(((MyConfiguration) configuration).getPlatformConfigPath()+":can't find "+entityName+"'s metadata.");
		}
		return md;
	}

	public List query(Criteria c, int skip, int max) throws SystemException {
		c.setFacade(this);
		String sql = c.toSqlString();
		System.out.println(sql);
		return this.query(c.getReturnType(), sql, c.getParameters(), skip, max);
	}

	public List query(Criteria c) throws SystemException {
		c.setFacade(this);
		String sql = c.toSqlString();
		System.out.println(sql);
		return this.query(c.getReturnType(), sql, c.getParameters());
	}

	public List query(Class type, Criteria c) throws SystemException {
		c.setFacade(this);
		return this.query(type, c.toSqlString(), c.getParameters());
	}

	public int executeProcedure(String procedureName, ProcedureParameter pp)
			throws SystemException {
		ProcedureMappedStatementBuilder builder = new ProcedureMappedStatementBuilder(configuration, procedureName,pp);
		Map parameterObj = (Map)builder.getParameterObject();
		int result = this.openSession().update(builder.build(), parameterObj);
		int size = pp.size();
		for (int i=0;i<size;i++){
			Object value = parameterObj.get("prop"+i);
			pp.setValue(i, value);
		}
		
		return result;
	}
}
