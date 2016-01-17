package com.codes.persistence.hibernate.test;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Pagination {
	private Integer pageNo;
	private Integer pageSize;
	private Integer total; 
	private List rows = new ArrayList();
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}
