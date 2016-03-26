package com.payadd.framework.ddl.mybatis;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MySqlSessionFactoryBuilder extends SqlSessionFactoryBuilder {
	public SqlSessionFactory build(Configuration config) {
		return new MySqlSessionFactory(config);
	}
}
