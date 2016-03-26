package com.payadd.framework.ddl.mybatis;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;

import com.payadd.framework.ddl.ParameterConfig;
import com.payadd.framework.ddl.ProcedureParameter;

public class ProcedureMappedStatementBuilder extends MappedStatementBuilder {
	private static final Map<Class,JdbcType> TYPE_MAP= new HashMap<Class,JdbcType>();
	
	private Object parameterObject;
	private ParameterMap parameterMap;
	private String procedureName;
	private ProcedureParameter pp;
	
	public ProcedureMappedStatementBuilder(Configuration configuration,String procedureName,ProcedureParameter pp){
		this.configuration  = configuration;
		this.procedureName = procedureName;
		this.pp = pp;
		this.parameterObject = toParameterObject(pp);
		this.statementType = StatementType.CALLABLE;
	}
//
//	public EntityMetadata getEntityMetadata(){
//		if (metadata!=null)return metadata;
//		metadata = ((MyConfiguration)this.configuration).getEntityMap().get(entityName);
//		return metadata;
//	}
	@Override
	public ResultMap getResultMap() {
		return null;
	}

	@Override
	public ParameterMap getParameterMap() {
		if (parameterMap!=null)return this.parameterMap;
		
		List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
		int size = this.pp!=null?this.pp.size():0;
		for (int i=0;i<size;i++){
			ParameterConfig pc = this.pp.get(i);
			Class javaType = pc.getType();
			JdbcType jdbcType = getJdbcType(javaType);
			if (javaType == null)javaType = Object.class;
			
			ParameterMapping.Builder pmBuilder = new ParameterMapping.Builder(configuration, "prop"+i, javaType);
			switch (pc.getMode()){
			case ProcedureParameter.IN_MODE:pmBuilder.mode(ParameterMode.IN);break;
			case ProcedureParameter.OUT_MODE:pmBuilder.mode(ParameterMode.OUT);break;
			case ProcedureParameter.IN_OUT_MODE:pmBuilder.mode(ParameterMode.INOUT);break;
			}
			pmBuilder.jdbcType(jdbcType);
			
			parameterMappings.add(pmBuilder.build());
		}
		ParameterMap.Builder inlineParameterMapBuilder = new ParameterMap.Builder(
				configuration, "parameter_map_id",
				Map.class, parameterMappings);
		this.parameterMap = inlineParameterMapBuilder.build();
		
		return this.parameterMap;
	}

	@Override
	public String getSql() {
		StringBuffer sql = new StringBuffer("{call ");
		sql.append(this.procedureName).append("(");
		int size = this.pp!=null?this.pp.size():0;
		for (int i=0;i<size;i++){
			if (i==0)sql.append("?");
			else sql.append(",?");
		}
		sql.append(" )}");
		
		return sql.toString();
	}
	private Object toParameterObject(ProcedureParameter pp){
		if (pp==null)return new HashMap();
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		int size = pp.size();
		for (int i=0;i<size;i++)
			paramMap.put("prop"+i, pp.get(i).getValue());
		return paramMap;
	}
	public Object getParameterObject(){
		return this.parameterObject;
	}
	
	private JdbcType getJdbcType(Class type){
		if (type==null)return null;
		return TYPE_MAP.get(type);
	}
	static {
		TYPE_MAP.put(Integer.class, JdbcType.INTEGER);
		TYPE_MAP.put(Float.class, JdbcType.FLOAT);
		TYPE_MAP.put(Double.class, JdbcType.DOUBLE);
		//TYPE_MAP.put(Numeric.class, new Long(Types.NUMERIC));
		TYPE_MAP.put(BigDecimal.class, JdbcType.DECIMAL);
		TYPE_MAP.put(String.class, JdbcType.VARCHAR);
		TYPE_MAP.put(Date.class, JdbcType.DATE);
		TYPE_MAP.put(Timestamp.class, JdbcType.TIMESTAMP);
		TYPE_MAP.put(Time.class, JdbcType.TIME);

		
	}
}
