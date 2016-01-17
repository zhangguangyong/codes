package com.codes.persistence.hibernate.dao;

import java.io.Serializable;

import com.codes.persistence.hibernate.domain.Entity;
import com.codes.persistence.hibernate.domain.Page;
import com.codes.persistence.hibernate.domain.Pageable;
import com.codes.persistence.hibernate.domain.Sort;

/**
 * 实体DAO的标识接口
 * 
 * @author Administrator
 *
 *         2015年10月27日 下午5:50:55
 */
public interface EntityDao extends Dao {
	/**
	 * 统计实体所有记录
	 * 
	 * @return
	 */
	<T> long count(Class<T> entityClass);

	/**
	 * 根据实体主键删除
	 * 
	 * @param id
	 */
	<T> void delete(Class<T> entityClass, Serializable id);

	/**
	 * 根据多个主键删除
	 * 
	 * @param entities
	 */
	<T> void delete(Class<T> entityClass, Serializable[] ids);

	/**
	 * 根据多个主键删除
	 * @param ids
	 */
	<T> void delete(Class<T> entityClass, Iterable<?> ids);

	/**
	 * 删除所有
	 */
	<T> void deleteAll(Class<T> entityClass);

	/**
	 * 根据主键判断实体是否存在
	 * 
	 * @param id
	 * @return
	 */
	<T> boolean exists(Class<T> entityClass, Serializable id);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	<T> Iterable<T> findAll(Class<T> entityClass);

	/**
	 * 根据多个主键查询
	 * 
	 * @param ids
	 * @return
	 */
	<T, ID extends Serializable> Iterable<T> find(Class<T> entityClass, ID[] ids);
	
	/**
	 * 根据多个主键查询
	 * 
	 * @param ids
	 * @return
	 */
	<T> Iterable<T> find(Class<T> entityClass, Iterable<?> ids);

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	<T> T find(Class<T> entityClass, Serializable id);

	/**
	 * 新增或更新多个实体
	 * 
	 * @param entities
	 * @return
	 */
	void saveOrUpdate(Iterable<?> entities);

	/**
	 * 新增或更新一个实体
	 * 
	 * @param entity
	 * @return
	 */
	Entity<? extends Serializable> saveOrUpdate(Entity<? extends Serializable> entity);

	/**
	 * 分页查询
	 * 
	 * @param pageable
	 * @return
	 */
	<T> Page<T> find(Class<T> entityClass, Pageable pageable);

	/**
	 * 排序查询
	 * 
	 * @param sort
	 * @return
	 */
	<T> Iterable<T> find(Class<T> entityClass, Sort sort);

}
