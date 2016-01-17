package com.codes.persistence.hibernate.domain;


/**
 * 可进行分页的标准
 * @author zhangguangyong
 *
 * 2015年10月27日 下午7:10:33
 */
public interface Pageable {
	/**
	 * 页数
	 * @return
	 */
	int getPageNumber();

	/**
	 * 页大小
	 * @return
	 */
	int getPageSize();
	
	/**
	 * 页面排序
	 * @return
	 */
	Sort getSort();
	
}
