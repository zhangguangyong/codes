package com.codes.persistence.hibernate.dao;

import java.util.List;
import java.util.Map;

import com.codes.persistence.hibernate.domain.Page;
import com.codes.persistence.hibernate.domain.Pageable;

/**
 * Hibernate HQL 接口
 * 
 * @author zhangguangyong
 *
 *         2015年10月31日 下午3:02:13
 */
public interface HqlDao extends Dao {

	// 对数据库的读取----------------------------------------------------
	/**
	 * HQL 查询
	 * 
	 * @param ql
	 *            HQL语句
	 * @return
	 */
	List<?> find(String ql);

	/**
	 * HQL 使用[占位符(?)参数]查询
	 * 
	 * @param ql
	 *            HQL语句
	 * @param arg0
	 *            第一个参数
	 * @param rest
	 *            剩余的参数
	 * @return
	 */
	List<?> find(String ql, Object arg0, Object... rest);

	/**
	 * HQL 使用[占位符(?)参数]查询
	 * 
	 * @param ql
	 *            HQL语句
	 * @param args
	 *            所有的参数
	 * @return
	 */
	List<?> find(String ql, Object[] args);

	/**
	 * HQL 使用[命名(:parameterName)参数]查询
	 * 
	 * @param ql
	 *            HQL语句
	 * @param namedParameter
	 *            HQL所有的命名参数
	 * @return
	 */
	List<?> find(String ql, Map<String, Object> namedParameter);

	/**
	 * HQL 使用[包装查询参数对象]查询, 如果以上几个查询都无法满足需求的时候，可以使用此接口查询
	 * 
	 * @param parameterWrap
	 *            包装查询参数对象
	 * @return
	 */
	List<?> find(QueryParameterWrap parameterWrap);

	// 分页
	/**
	 * HQL 使用[占位符(?)参数]进行分页查询
	 * 
	 * @param ql
	 *            HQL语句
	 * @param pageable
	 *            用于分页的参数
	 * @return
	 */
	Page<?> findForPage(String ql, Pageable pageable);

	/**
	 * HQL 使用[占位符(?)参数]进行分页查询
	 * 
	 * @param ql
	 *            HQL语句
	 * @param pageable
	 *            用于分页的参数
	 * @param arg0
	 *            HQL的第一个参数
	 * @param rest
	 *            HQL的剩余参数
	 * @return
	 */
	Page<?> findForPage(String ql, Pageable pageable, Object arg0,
			Object... rest);

	/**
	 * HQL 使用[占位符(?)参数]进行分页查询
	 * 
	 * @param ql
	 *            HQL语句
	 * @param pageable
	 *            用于分页的参数
	 * @param args
	 *            HQL的所有参数
	 * @return
	 */
	Page<?> findForPage(String ql, Pageable pageable, Object[] args);

	/**
	 * HQL 使用[命名(:parameterName)参数]查询
	 * 
	 * @param ql
	 *            HQL语句
	 * @param pageable
	 *            用于分页的参数
	 * @param namedParameter
	 *            HQL所有的命名参数
	 * @return
	 */
	Page<?> findForPage(String ql, Pageable pageable,
			Map<String, Object> namedParameter);

	/**
	 * HQL 使用[包装查询参数对象]进行分页查询, 如果以上几个分页查询都无法满足需求的时候，可以使用此接口查询
	 * 
	 * @param parameterWrap
	 *            包装查询参数对象
	 * @return
	 */
	Page<?> findForPage(QueryParameterWrap parameterWrap);

	// 对数据库的写入----------------------------------------------------
	/**
	 * HQL 更新(这里的更新包含了：insert, update, delete 三种操作, 以下所有update接口也是一样的)
	 * 
	 * @param ql
	 * @return
	 */
	int update(String ql);

	/**
	 * HQL 使用[占位符(?)参数]进行更新
	 * 
	 * @param ql
	 *            HQL语句
	 * @param args
	 *            HQL所有的参数
	 * @return
	 */
	int update(String ql, Object[] args);

	/**
	 * HQL 使用[占位符(?)参数]进行更新
	 * 
	 * @param ql
	 *            HQL语句
	 * @param arg0
	 *            HQL第一个参数
	 * @param rest
	 *            HQL剩余的参数
	 * @return
	 */
	int update(String ql, Object arg0, Object... rest);

	/**
	 * HQL 使用[命名(:parameterName)参数]进行更新
	 * 
	 * @param ql
	 *            HQL语句
	 * @param namedParameter
	 *            HQL所有的命名参数
	 * @return
	 */
	int update(String ql, Map<String, Object> namedParameter);

	/**
	 * HQL 使用[包装查询参数对象]进行更新, 如果以上几个更新接口都无法满足需求的时候，可以使用此接口
	 * 
	 * @param parameterWrap
	 *            包装查询参数对象
	 * @return
	 */
	int update(QueryParameterWrap parameterWrap);

}
