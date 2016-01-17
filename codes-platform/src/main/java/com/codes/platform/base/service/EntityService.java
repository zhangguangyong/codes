package com.codes.platform.base.service;

import java.io.Serializable;

import com.codes.persistence.hibernate.domain.Entity;
import com.codes.persistence.hibernate.domain.Page;
import com.codes.persistence.hibernate.domain.Pageable;
import com.codes.persistence.hibernate.domain.Sort;

/**
 * 实体Service接口
 * 
 * @author zhangguangyong
 *
 *         2015年11月5日 下午3:05:34
 */
public interface EntityService<T extends Entity<ID>, ID extends Serializable> {

	void save(T entity);

	boolean exists(ID id);

	void delete(ID id);

	void delete(ID[] ids);

	void delete(Iterable<ID> ids);

	T find(ID id);

	Iterable<T> findAll();

	Iterable<T> find(ID[] ids);

	Iterable<T> find(Iterable<ID> ids);

	long count();

	Page<T> find(Pageable pageable);

	Iterable<T> find(Sort sort);

}
