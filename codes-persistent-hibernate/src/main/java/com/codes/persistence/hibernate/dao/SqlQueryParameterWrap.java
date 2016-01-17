package com.codes.persistence.hibernate.dao;

import static com.codes.common.util.$.isNull;
import static com.codes.common.util.$.notEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codes.persistence.hibernate.domain.Pageable;

/**
 * Hibernate Native SQL 条件封装
 * 
 * @author zhangguangyong
 *
 *         2015年10月30日 上午9:46:05
 */
@SuppressWarnings("all")
public class SqlQueryParameterWrap extends QueryParameterWrap {

	/** 列与对应类型的映射 */
	private Map<String, Class> columnAndTypeMapping = new HashMap<String, Class>();

	/** 查询结果转为实体的列表 */
	private List<Class> entityClassList = new ArrayList<Class>();

	public static SqlQueryParameterWrap getInstance() {
		return new SqlQueryParameterWrap();
	}

	public static SqlQueryParameterWrap getInstance(String queryString) {
		return new SqlQueryParameterWrap(queryString);
	}

	public SqlQueryParameterWrap() {
	}

	public SqlQueryParameterWrap(String queryString) {
		this.queryString = queryString;
	}

	public SqlQueryParameterWrap addScalar(String column, Class type) {
		this.columnAndTypeMapping.put(column, type);
		return this;
	}

	public SqlQueryParameterWrap addEntity(Class entityClass) {
		this.entityClassList.add(entityClass);
		return this;
	}

	public Map<String, Class> getColumnAndTypeMapping() {
		return columnAndTypeMapping;
	}

	public List<Class> getEntityClassList() {
		return entityClassList;
	}

	public SqlQueryParameterWrap setColumnAndTypeMapping(
			Map<String, Class> columnAndTypeMapping) {
		this.columnAndTypeMapping = columnAndTypeMapping;
		return this;
	}

	public SqlQueryParameterWrap setEntityClassList(List<Class> entityClassList) {
		this.entityClassList = entityClassList;
		return this;
	}
	
	// -----------------------------
	public SqlQueryParameterWrap addArgs(Object arg0, Object...rest) {
		super.addArgs(arg0, rest);
		return this;
	}

	public SqlQueryParameterWrap addArgs(Object[] args) {
		super.addArgs(args);
		return this;
	}
	
	/**
	 * 添加占位符参数 ?->占位符参数
	 * 
	 * @param value
	 * @return
	 */
	public SqlQueryParameterWrap addParameter(Object value) {
		super.addParameter(value);
		return this;
	}

	/**
	 * 添加占位符参数 ?->占位符参数
	 * 
	 * @param position
	 * @param value
	 * @return
	 */
	public SqlQueryParameterWrap addParameter(int position, Object value) {
		super.addParameter(position, value);
		return this;
	}

	/**
	 * 添加命名的参数 :propertyName ->命名的参数
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public SqlQueryParameterWrap addParameter(String name, Object value) {
		super.addParameter(name, value);
		return this;
	}

	/**
	 * 添加命名的参数 :propertyName ->命名的参数
	 * 
	 * @param name
	 * @param values
	 * @return
	 */
	public SqlQueryParameterWrap addParameters(String name, Collection<?> values) {
		super.addParameters(name, values);
		return this;
	}

	/**
	 * 添加命名的参数 :propertyName ->命名的参数
	 * 
	 * @param name
	 * @param values
	 * @return
	 */
	public SqlQueryParameterWrap addParameters(String name, Object[] values) {
		super.addParameters(name, values);
		return this;
	}

	/**
	 * 添加命名的参数 :propertyName ->命名的参数
	 * 
	 * @param name
	 * @param first
	 *            第一个，不能为空
	 * @param second
	 *            第二个，不能为空
	 * @param rest
	 *            后面的，可为空
	 * @return
	 */
	public SqlQueryParameterWrap addParameters(String name, Object first, Object second,
			Object... rest) {
		super.addParameters(name, first, second, rest);
		return this;
	}
	
	public SqlQueryParameterWrap setQueryString(String queryString) {
		this.queryString = queryString;
		return this;
	}

	public SqlQueryParameterWrap setArgs(List<Object> args) {
		this.args = args;
		return this;
	}

	public SqlQueryParameterWrap setNamedParameter(Map<String, Object> namedParameter) {
		this.namedParameter = namedParameter;
		return this;
	}

	public SqlQueryParameterWrap setPageable(Pageable pageable) {
		this.pageable = pageable;
		return this;
	}

	public SqlQueryParameterWrap setResultTransformerType(Class<?> resultTransformerType) {
		this.resultTransformerType = resultTransformerType;
		return this;
	}
	
	

}
