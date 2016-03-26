package com.payadd.framework.ddl.executor.result;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;

/**
 * ResultContext扩展
 * @author YangYi
 * @time 2012-8-13
 */
public class MyResultContext implements ResultContext {

	private Map<String, Object> resultMap;
	private int resultCount;
	private boolean stopped;
	private boolean cleared;
	 
	public MyResultContext() {
		resultCount = 0;
		stopped = false;
		cleared = true;
		resultMap = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getResultObject() {
		return resultMap;
	}

	public int getResultCount() {
		return resultCount;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void stop() {
		this.stopped = true;
	}

	public void nextResultObject(Object resultObject, String aliasName) {
		if (resultMap == null) resultMap = new HashMap<String, Object>();
		resultMap.put(aliasName, resultObject);
		if (cleared) {
			resultCount++;
			cleared = false;
		}
	}
	
	public void clear() {
		if (this.resultMap != null) {
			this.resultMap = new HashMap<String, Object>();
		}
		cleared = true;
	}
}
