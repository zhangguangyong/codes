package com.codes.persistence.hibernate.dao;

import java.io.Serializable;

import com.codes.persistence.hibernate.domain.Entity;
import com.codes.persistence.hibernate.domain.Page;
import com.codes.persistence.hibernate.domain.Pageable;
import com.codes.persistence.hibernate.domain.Sort;

/**
 * 分页与排序 Data Access Object
 * 
 * @author zhangguangyong
 *
 *         2015年10月27日 下午7:48:17
 */
public interface PagingAndSortingDao<T extends Entity<ID>, ID extends Serializable> extends CurdDao<T, ID> {
	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	Page<T> findAll(Pageable pageable);

	/**
	 * 排序查询
	 * @param sort
	 * @return
	 */
	Iterable<T> findAll(Sort sort);

}
