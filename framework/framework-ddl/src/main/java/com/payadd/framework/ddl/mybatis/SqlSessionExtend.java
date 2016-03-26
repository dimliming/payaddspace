package com.payadd.framework.ddl.mybatis;

import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;

public interface SqlSessionExtend extends SqlSession {
	public List selectList(MappedStatement ms, Object parameter);
	public int update(MappedStatement ms,Object parameter);
	public List selectList(MappedStatement ms, Object parameter, ResultHandler handler);
}
