package com.codes.persistence.hibernate.domain;

import java.util.List;

/**
 * 页面内容接口
 * @author zhangguangyong
 *
 * 2015年10月27日 下午7:09:11
 */
public interface Page<T> {
	/**
	 * 总记录数
	 * @return
	 */
	long getTotalRows();

	/**
	 * 总页数
	 * @return
	 */
	int getTotalPages();
	
	/**
	 * 页面内容
	 * @return
	 */
	List<T> getContent();
}
