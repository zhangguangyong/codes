package com.codes.persistence.hibernate.dao;

import java.io.Serializable;

import com.codes.persistence.hibernate.domain.Entity;

/**
 * 基于实体的增删改查 
 * @author Administrator
 *
 * 2015年10月27日 下午5:51:44
 */
public interface CurdDao<T extends Entity<ID>, ID extends Serializable> {
	/**
	 * 统计实体所有记录
	 * @return
	 */
	long count();

	/**
	 * 根据实体主键删除
	 * @param id
	 */
	void delete(ID id);
	
	/**
	 * 删除一个实体集
	 * @param entities
	 */
	void delete(Iterable<? extends T> entities);
	
	/**
	 * 删除一个实体
	 * @param entity
	 */
	void delete(T entity);
	
	/**
	 * 删除所有实体
	 */
	void deleteAll();

	/**
	 * 根据实体主键判断实体是否存在
	 * @param id
	 * @return
	 */
	boolean exists(ID id);

	/**
	 * 查询所有的实体集
	 * @return
	 */
	Iterable<T> findAll();
	
	/**
	 * 查询主键集对应的实体
	 * @param ids
	 * @return
	 */
	Iterable<T> findAll(Iterable<ID> ids);

	/**
	 * 根据主键查询一个实体
	 * @param id
	 * @return
	 */
	T findOne(ID id);

	/**
	 * 新增或更新实体集
	 * @param entities
	 * @return
	 */
	Iterable<T> saveOrUpdate(Iterable<T> entities);
	
	/**
	 * 新增或更新一个实体
	 * @param entity
	 * @return
	 */
	T saveOrUpdate(T entity);
}
