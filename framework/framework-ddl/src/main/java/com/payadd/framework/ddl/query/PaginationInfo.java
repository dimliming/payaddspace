package com.payadd.framework.ddl.query;

public class PaginationInfo {
	protected long totalRecord=-1;
	protected int currentPage=1;
	protected int totalPage=-1;
	protected int pageSize=10;//默认每次查10条记录，可以在程序中自由设置
	protected boolean isPagination = true;
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isPagination() {
		return isPagination;
	}
	public void setPagination(boolean isPagination) {
		this.isPagination = isPagination;
	}
	
	
}
