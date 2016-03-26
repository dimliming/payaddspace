package com.payadd.framework.ddl.query.criterion;

import java.util.Comparator;

public class PriorityComparator implements Comparator {

	public int compare(Object arg0, Object arg1) {
		if (arg0==null&&arg1==null)return 0;
		if (arg0==null)return -1;
		if (arg1==null)return 1;
		
		Priority pri0 = (Priority)arg0;
		Priority pri1 = (Priority)arg1;
		
		if (pri0.getPriority()==-1&&pri1.getPriority()==-1)return 0;
		if (pri0.getPriority()==-1)return 1;
		if (pri1.getPriority()==-1)return -1;
		
		if (pri0.getPriority()==pri1.getPriority())return 0;
		if (pri0.getPriority()>pri1.getPriority())return 1;
		if (pri0.getPriority()<pri1.getPriority())return -1;
		
		return 0;
	}

}
