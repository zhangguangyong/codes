package com.codes.persistence.hibernate.dao;
import static com.codes.common.util.$.notEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codes.persistence.hibernate.domain.Pageable;
import com.google.common.collect.Lists;

/**
 * Hibernate HQL 条件封装
 * 
 * @author zhangguangyong
 *
 *         2015年10月30日 上午9:46:05
 */
public class QueryParameterWrap {
	/** 查询字符串 */
	protected String queryString;
	/** 占位符参数 */
	protected List<Object> args = new ArrayList<Object>();
	/** 命名的参数 */
	protected Map<String, Object> namedParameter = new HashMap<String, Object>();
	/** 分页参数*/
	protected Pageable pageable;
	/** 查询结果转换类型（支持：Map.class, List.class） */
	protected Class<?> resultTransformerType;

	public static QueryParameterWrap getInstance() {
		return new QueryParameterWrap();
	}

	public static QueryParameterWrap getInstance(String queryString) {
		return new QueryParameterWrap(queryString);
	}

	public QueryParameterWrap() {
	}

	public QueryParameterWrap(String queryString) {
		this.queryString = queryString;
	}

	public QueryParameterWrap addArgs(Object arg0, Object...rest) {
		return addArgs(Lists.asList(arg0, rest).toArray());
	}

	public QueryParameterWrap addArgs(Object[] args) {
		this.args.addAll(Arrays.asList(args));
		return this;
	}
	
	/**
	 * 添加占位符参数 ?->占位符参数
	 * 
	 * @param value
	 * @return
	 */
	public QueryParameterWrap addParameter(Object value) {
		this.args.add(value);
		return this;
	}

	/**
	 * 添加占位符参数 ?->占位符参数
	 * 
	 * @param position
	 * @param value
	 * @return
	 */
	public QueryParameterWrap addParameter(int position, Object value) {
		this.args.add(position, value);
		return this;
	}

	/**
	 * 添加命名的参数 :propertyName ->命名的参数
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public QueryParameterWrap addParameter(String name, Object value) {
		this.namedParameter.put(name, value);
		return this;
	}

	/**
	 * 添加命名的参数 :propertyName ->命名的参数
	 * 
	 * @param name
	 * @param values
	 * @return
	 */
	public QueryParameterWrap addParameters(String name, Collection<?> values) {
		this.namedParameter.put(name, values);
		return this;
	}

	/**
	 * 添加命名的参数 :propertyName ->命名的参数
	 * 
	 * @param name
	 * @param values
	 * @return
	 */
	public QueryParameterWrap addParameters(String name, Object[] values) {
		this.namedParameter.put(name, values);
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
	public QueryParameterWrap addParameters(String name, Object first, Object second,
			Object... rest) {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(first);
		parameters.add(second);
		if (notEmpty(rest)) {
			parameters.addAll(Arrays.asList(rest));
		}
		return addParameters(name, parameters);
	}

	public String getQueryString() {
		return queryString;
	}

	public List<Object> getArgs() {
		return args;
	}

	public Map<String, Object> getNamedParameter() {
		return namedParameter;
	}
	
	public Pageable getPageable() {
		return pageable;
	}

	public Class<?> getResultTransformerType() {
		return resultTransformerType;
	}

	public QueryParameterWrap setQueryString(String queryString) {
		this.queryString = queryString;
		return this;
	}

	public QueryParameterWrap setArgs(List<Object> args) {
		this.args = args;
		return this;
	}

	public QueryParameterWrap setNamedParameter(Map<String, Object> namedParameter) {
		this.namedParameter = namedParameter;
		return this;
	}

	public QueryParameterWrap setPageable(Pageable pageable) {
		this.pageable = pageable;
		return this;
	}

	public QueryParameterWrap setResultTransformerType(Class<?> resultTransformerType) {
		this.resultTransformerType = resultTransformerType;
		return this;
	}

}
