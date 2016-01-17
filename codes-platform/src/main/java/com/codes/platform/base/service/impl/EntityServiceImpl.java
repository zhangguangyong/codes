package com.codes.platform.base.service.impl;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.codes.common.util.Reflections;
import com.codes.persistence.hibernate.domain.Entity;
import com.codes.persistence.hibernate.domain.Page;
import com.codes.persistence.hibernate.domain.Pageable;
import com.codes.persistence.hibernate.domain.Sort;
import com.codes.platform.base.service.BaseService;
import com.codes.platform.base.service.EntityService;

/**
 * 实体Service接口实现
 * 
 * @author zhangguangyong
 *
 *         2015年11月5日 下午3:36:41
 */
@SuppressWarnings("all")
public class EntityServiceImpl<T extends Entity<ID>, ID extends Serializable>
		extends BaseService implements EntityService<T, ID> {

	private Class<T> entityClass;
	
	public EntityServiceImpl() {
		this.entityClass = Reflections.getGeneric(getClass());
	}
	
	@Override
	public void save(T entity) {
		getEntityDao().saveOrUpdate(entity);
	}

	@Override
	public boolean exists(ID id) {
		return getEntityDao().exists(entityClass, id);
	}

	@Override
	public void delete(ID id) {
		getEntityDao().delete(entityClass, id);
	}

	@Override
	public void delete(ID[] ids) {
		getEntityDao().delete(entityClass, ids);
	}

	@Override
	public void delete(Iterable<ID> ids) {
		getEntityDao().delete(entityClass, ids);
	}

	@Override
	public T find(ID id) {
		return getEntityDao().find(entityClass, id);
	}

	@Override
	public Iterable<T> findAll() {
		return getEntityDao().findAll(entityClass);
	}

	@Override
	public Iterable<T> find(ID[] ids) {
		return getEntityDao().find(entityClass, ids);
	}

	@Override
	public Iterable<T> find(Iterable<ID> ids) {
		return getEntityDao().find(entityClass, ids);
	}

	@Override
	public long count() {
		return getEntityDao().count(entityClass);
	}

	@Override
	public Page<T> find(Pageable pageable) {
		return getEntityDao().find(entityClass, pageable);
	}

	@Override
	public Iterable<T> find(Sort sort) {
		return getEntityDao().find(entityClass, sort);
	}

}
