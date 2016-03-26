package com.payadd.framework.ddl.executor.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

/**
 * ResultHandler扩展
 * @author YangYi
 * @time 2012-8-13
 * 
 * @property List<Map> list 如果有多表查询，则通过多个表别名与resultMap的映射关系(Map<Alias, ResultMap>)，获取别名与对象实例映射的列表数据
 * @property Map<Alias, ResultMap> metaMap 存储查询表别名与对应ResultMap的映射关系
 */
public class MyResultHandler implements ResultHandler {
	private List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
	private Map<String, ResultMap> metaMap;
	
	public MyResultHandler(Map<String, ResultMap> metaMap) {
		this.metaMap = metaMap;
	}

	@SuppressWarnings("unchecked")
	public void handleResult(ResultContext context) {
		if (context == null) return;
		resultList.add((Map<String, Object>) context.getResultObject());
	}
	
	public List<Map<String, Object>> getResultList() {
	    return resultList;
	}

	public Map<String, ResultMap> getMetaMap() {
		return metaMap;
	}
}
