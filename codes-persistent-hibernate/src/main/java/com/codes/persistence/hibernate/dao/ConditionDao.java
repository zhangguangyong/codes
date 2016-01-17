package com.codes.persistence.hibernate.dao;

import java.util.List;

import com.codes.persistence.hibernate.criteria.Condition;
import com.codes.persistence.hibernate.domain.Page;
import com.codes.persistence.hibernate.domain.Pageable;
import com.codes.persistence.hibernate.domain.Sort;

/**
 * 实体属性查询DAO，底层是使用Hibernate的Criteria查询接口
 * 
 * @author zhangguangyong
 *
 *         2015年10月31日 下午4:19:06
 */
public interface ConditionDao {

	/**
	 * 实体属性查询
	 * 
	 * @param condition
	 *            实体属性条件
	 * @param persistentClass
	 *            被查询的实体类
	 * @return
	 */
	List<?> find(Condition condition, Class<?> persistentClass);

	/**
	 * 实体属性查询
	 * 
	 * @param condition
	 *            实体属性条件
	 * @param persistentClass
	 *            被查询的实体类
	 * @param sort
	 *            排序参数
	 * @return
	 */
	List<?> find(Condition condition, Class<?> persistentClass, Sort sort);

	/**
	 * 实体属性查询
	 * 
	 * @param condition
	 *            实体属性条件
	 * @param persistentClass
	 *            被查询的实体类
	 * @param pageable
	 *            分页参数(包含了排序参数)
	 * @return
	 */
	Page<?> find(Condition condition, Class<?> persistentClass,
			Pageable pageable);

}
