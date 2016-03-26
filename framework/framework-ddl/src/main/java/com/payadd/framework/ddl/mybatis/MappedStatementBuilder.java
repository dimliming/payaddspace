package com.payadd.framework.ddl.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.session.Configuration;

public abstract class MappedStatementBuilder {
	protected Configuration configuration;
	protected Integer fetchSize = new Integer(10);
	protected StatementType statementType = StatementType.PREPARED;;
	protected SqlCommandType sqlCommandType;
	protected boolean flushCache = false;
	protected boolean useCache = false;
	protected Cache cache = null;
	
    
	public abstract ResultMap getResultMap();
	
	public abstract ParameterMap getParameterMap();
	
	public abstract String getSql();
	
	public MappedStatement build(){
		ResultMap rm = getResultMap();
		ParameterMap pm = getParameterMap();
		
		KeyGenerator keyGenerator = new NoKeyGenerator();
		String id="auto_gen_statement";
		String keyProperty = "";
		SqlSource sqlSource = new NativeSqlSource(configuration,getSql(),pm);
	    MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, id, sqlSource, sqlCommandType);
	    statementBuilder.resource("");
	    statementBuilder.fetchSize(this.fetchSize);
	    statementBuilder.statementType(statementType);
	    statementBuilder.keyGenerator(keyGenerator);
	    statementBuilder.keyProperty(keyProperty);
	    statementBuilder.timeout(10000);

	    statementBuilder.parameterMap(pm);
	    
	    if (rm!=null){
		    List<ResultMap> rms = new ArrayList<ResultMap>();
		    rms.add(rm);
		    statementBuilder.resultMaps(rms);
	    }
		statementBuilder.flushCacheRequired(flushCache);
		statementBuilder.useCache(useCache);
		statementBuilder.cache(cache);
		
	    MappedStatement statement = statementBuilder.build();
	    return statement;
	}
	

}
