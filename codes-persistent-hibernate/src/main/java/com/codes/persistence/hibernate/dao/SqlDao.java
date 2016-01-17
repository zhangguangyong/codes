package com.codes.persistence.hibernate.dao;

import java.util.List;
import java.util.Map;

/**
 * Hibernate Native SQL 接口
 * 
 * @author zhangguangyong
 *
 *         2015年11月1日 上午4:13:16
 */
public interface SqlDao extends HqlDao {

	// 单行单列->返回Object
	/**
	 * SQL 单行单列查询
	 * 
	 * @param sql
	 *            SQL语句
	 * @param requiredType
	 *            返回结果的类型
	 * @return
	 */
	<T> T findForObject(String sql, Class<T> requiredType);

	/**
	 * SQL 单行单列查询,使用占位符参数(?)
	 * 
	 * @param sql
	 *            SQL语句
	 * @param requiredType
	 *            返回结果的类型
	 * @param args
	 *            SQL所有的参数
	 * @return
	 */
	<T> T findForObject(String sql, Class<T> requiredType, Object[] args);

	/**
	 * SQL 单行单列查询,使用占位符参数(?)
	 * 
	 * @param sql
	 *            SQL语句
	 * @param requiredType
	 *            返回结果的类型
	 * @param arg0
	 *            SQL第一个参数
	 * @param rest
	 *            SQL剩余的参数
	 * @return
	 */
	<T> T findForObject(String sql, Class<T> requiredType, Object arg0,
			Object... rest);

	/**
	 * SQL 单行单列查询,使用命名参数(:parameterName)
	 * 
	 * @param sql
	 *            SQL语句
	 * @param requiredType
	 *            返回结果的类型
	 * @param namedParameter
	 *            SQL所有的命名参数
	 * @return
	 */
	<T> T findForObject(String sql, Class<T> requiredType,
			Map<String, Object> namedParameter);

	// 单行多列->返回Map
	/**
	 * SQL 单行多列查询
	 * 
	 * @param sql
	 *            SQL语句
	 * @return
	 */
	Map<String, Object> findForMap(String sql);

	/**
	 * SQL 单行多列查询,使用占位符参数(?)
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            SQL所有的参数
	 * @return
	 */
	Map<String, Object> findForMap(String sql, Object[] args);

	/**
	 * SQL 单行多列查询,使用占位符参数(?)
	 * 
	 * @param sql
	 * @param arg0
	 *            SQL第一个参数
	 * @param rest
	 *            SQL剩余的参数
	 * @return
	 */
	Map<String, Object> findForMap(String sql, Object arg0, Object... rest);

	/**
	 * SQL 单行多列查询,使用命名参数(:parameterName)
	 * 
	 * @param sql
	 *            SQL语句
	 * @param namedParameter
	 *            SQL所有的命名参数
	 * @return
	 */
	Map<String, Object> findForMap(String sql,
			Map<String, Object> namedParameter);

	// 多行单列->返回单值集合
	/**
	 * SQL 单列多行查询
	 * 
	 * @param sql
	 *            SQL语句
	 * @param elementType
	 *            返回结果集的元素类型
	 * @return
	 */
	<T> List<T> findForList(String sql, Class<T> elementType);

	/**
	 * SQL 单列多行查询
	 * 
	 * @param sql
	 *            SQL语句
	 * @param elementType
	 *            返回结果集的元素类型
	 * @param args
	 *            SQL所有的参数
	 * @return
	 */
	<T> List<T> findForList(String sql, Class<T> elementType, Object[] args);

	/**
	 * SQL 单列多行查询
	 * 
	 * @param sql
	 *            SQL语句
	 * @param elementType
	 *            返回结果集的元素类型
	 * @param arg0
	 *            SQL第一个参数
	 * @param rest
	 *            SQL剩余的参数
	 * @return
	 */
	<T> List<T> findForList(String sql, Class<T> elementType, Object arg0,
			Object... rest);

	/**
	 * SQL 单列多行查询,使用命名参数(:parameterName)
	 * 
	 * @param sql
	 *            SQL语句
	 * @param elementType
	 *            返回结果集的元素类型
	 * @param namedParameter
	 *            SQL所有的命名参数
	 * @return
	 */
	<T> List<T> findForList(String sql, Class<T> elementType,
			Map<String, Object> namedParameter);

	// 对数据库的批量写入
	/**
	 * SQL 批量更新
	 * 
	 * @param sqls
	 *            需要更新的SQL语句集
	 * @return
	 */
	int[] batchUpdate(String... sqls);

	/**
	 * SQL 批量更新
	 * 
	 * @param sql
	 *            SQL语句
	 * @param argsList
	 *            批量更新的参数列表
	 * @return
	 */
	int[] batchUpdate(String sql, List<Object[]> argsList);

}
