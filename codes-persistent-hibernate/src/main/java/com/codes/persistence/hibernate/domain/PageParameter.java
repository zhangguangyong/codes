package com.codes.persistence.hibernate.domain;


/**
 * 分页参数
 * @author zhangguangyong
 *
 * 2015年10月27日 下午7:18:31
 */
public class PageParameter implements Pageable {
	private int pageNumber;
	private int pageSize;
	private Sort sort;
	
	public PageParameter() {
	}
	
	public PageParameter(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}



	@Override
	public int getPageNumber() {
		return pageNumber;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}
	
	
	

}
