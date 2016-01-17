package com.codes.persistence.hibernate.domain;

import java.util.List;
/**
 * 页面内容
 * @author zhangguangyong
 *
 * 2015年10月27日 下午7:17:19
 */
public class PageImpl<T> implements Page<T>{

	private long totalRows;
	private int totalPages;
	private List<T> content;
	
	public PageImpl() {
	}
	
	public PageImpl(long totalRows, List<T> content) {
		this.totalRows = totalRows;
		this.content = content;
	}

	@Override
	public long getTotalRows() {
		return totalRows;
	}

	@Override
	public int getTotalPages() {
		return totalPages;
	}

	@Override
	public List<T> getContent() {
		return content;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
	
	

}
