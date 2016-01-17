package com.codes.persistence.hibernate.dao.impl;

import static com.codes.common.util.$.notEmpty;
import static com.codes.common.util.$.notNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.hibernate.type.ByteType;
import org.hibernate.type.CharacterType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.ShortType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;

import com.codes.common.util.Reflections;
import com.codes.persistence.hibernate.dao.QueryParameterWrap;
import com.codes.persistence.hibernate.dao.SqlQueryParameterWrap;
import com.codes.persistence.hibernate.domain.Pageable;
import com.codes.persistence.hibernate.domain.Sort;
import com.codes.persistence.hibernate.domain.support.ExtendField;
import com.codes.persistence.hibernate.domain.support.ExtendFieldAnno;
import com.google.common.primitives.Primitives;

/**
 * 数据访问辅助类
 * 
 * @author zhangguangyong
 *
 *         2015年10月28日 下午10:57:11
 */
@SuppressWarnings("all")
public abstract class DaoHelper {

	/**
	 * 获取扩展字段名称
	 * 
	 * @param cls
	 * @param extendField
	 * @return
	 */
	public static String getExtendFieldName(Class<?> cls,
			ExtendField extendField) {
		Field[] fields = Reflections.getAllDeclaredFields(cls);
		for (Field field : fields) {
			ExtendFieldAnno anno = field.getAnnotation(ExtendFieldAnno.class);
			if (notNull(anno) && anno.value() == extendField) {
				return field.getName();
			}
		}
		return null;
	}

	/**
	 * 封装为 统计 查询语句, 针对 Native SQL
	 * @param queryString
	 * @return
	 */
	public static String toCountSql(String queryString) {
		return " select count(*) from (" + queryString + ") t_"+System.currentTimeMillis();
	}

	/**
	 * 封装为 统计 查询语句,针对 Hiberante Query Language
	 * @param queryString
	 * @return
	 */
	public static String toCountHql(String queryString) {
		return queryString.replaceFirst("(?i)^\\s*(select)?.*from",
				"select count(*) from");
	}
	
	/**
	 * 转为单列查询语句
	 * @param sql
	 * @return
	 */
	public static String toSingleColumnSql(String sql) {
		String tempSql = sql.trim();
		String regex = "(?i)^select(.*?)from";
		Matcher m = Pattern.compile(regex).matcher(tempSql);
		String columnAlias = SqlTemplate.SINGLE_COLUMN_ALIAS;
		if (m.find()) {
			columnAlias = m.group(1).trim().replaceAll("\\(\\s+", "(").replaceAll("\\s+\\)", ")").split("\\s+")[0]+ " AS " + columnAlias;
		}
		return tempSql.replaceFirst(regex, "select " + columnAlias + " from ");
	}
	
	/**
	 * 转换为Hibernate类型
	 * @param javaType
	 * @return
	 */
	public static Type toHibernateType(Class<?> javaType){
		if (javaType == byte.class || javaType == Byte.class) {
			return  ByteType.INSTANCE;
		} 
		if (javaType == char.class || javaType == Character.class) {
			return   CharacterType.INSTANCE;
		} 
		if (javaType == short.class || javaType == Short.class) {
			return  ShortType.INSTANCE;
		} 
		if (javaType == int.class || javaType == Integer.class) {
			return  IntegerType.INSTANCE;
		}
		if (javaType == long.class || javaType == Long.class) {
			return  LongType.INSTANCE;
		}
		if (javaType == float.class || javaType == Float.class) {
			return  FloatType.INSTANCE;
		} 
		if (javaType == double.class || javaType == Double.class) {
			return  DoubleType.INSTANCE;
		}
		if (javaType == String.class) {
			return  StringType.INSTANCE;
		} 
		if (javaType == Date.class) {
			return  DateType.INSTANCE;
		} 
		if (javaType == Timestamp.class) {
			return  TimestampType.INSTANCE;
		}
		throw new IllegalArgumentException("does not support type: " + javaType);
	}
	
	public static SQLQuery setParameter(SQLQuery query, SqlQueryParameterWrap parameterWrap){
		setParameter((Query)query, parameterWrap);
		setParameter(query, parameterWrap.getEntityClassList());
		setParameter(query, parameterWrap.getColumnAndTypeMapping());
		return query;
	}
	
	public static SQLQuery setParameter(SQLQuery query, Map<String, Class> columnAndTypeMapping){
		if( notEmpty(columnAndTypeMapping) ){
			Set<Entry<String,Class>> entrySet = columnAndTypeMapping.entrySet();
			for (Entry<String, Class> entry : entrySet) {
				query.addScalar(entry.getKey(), toHibernateType(entry.getValue()));
			}
		}
		return query;
	}
	
	public static SQLQuery setParameter(SQLQuery query, List<Class> entityClassList){
		if( notEmpty(entityClassList) ){
			for (Class entityClass : entityClassList) {
				query.addEntity(entityClass);
			}
		}
		return query;
	}
	
	/**
	 * 设置所有参数
	 * @param query
	 * @param parameterWrap
	 * @return
	 */
	public static Query setParameter(Query query, QueryParameterWrap parameterWrap){
		setBasicParameter(query, parameterWrap);
		setPageParameter(query, parameterWrap.getPageable());
		setResultTransformerType(query, parameterWrap.getResultTransformerType());
		return query;
	}
	
	/**
	 * 设置基础参数(除去分页外的参数)
	 * @param query
	 * @param parameterWrap
	 * @return
	 */
	public static Query setBasicParameter(Query query, QueryParameterWrap parameterWrap){
		List<Object> args = parameterWrap.getArgs();
		if( notEmpty(args) ){
			setParameter(query, args);
		}else if( notEmpty( parameterWrap.getNamedParameter() ) ){
			setParameter(query, parameterWrap.getNamedParameter());
		}
		return query;
	}
	
	public static Query setParameter(Query query, Object[] args){
		return setParameter(query, Arrays.asList(args));
	}
	
	public static Query setParameter(Query query, List<?> args){
		if( notEmpty(args) ){
			for (int i = 0; i < args.size(); i++) {
				query.setParameter(i, args.get(i));
			}
		}
		return query;
	}
	
	public static Query setParameter(Query query, Map<String, Object> namedParameter){
		if( notEmpty(namedParameter) ){
			Set<Entry<String,Object>> entrySet = namedParameter.entrySet();
			String name = null;
			Object value = null;
			for (Entry<String, Object> entry : entrySet) {
				name = entry.getKey();
				value = entry.getValue();
				if( value.getClass().isArray() ){
					// 如果基本类型数组，这里需要处理
					if( ! Primitives.isWrapperType( value.getClass() ) ){
						int length = Array.getLength(value);
						Object[] arr = new Object[length];
						for (int i = 0; i < arr.length; i++) {
							arr[i] = Array.get(value, i);
						}
						query.setParameterList(name, arr);
					}else{
						query.setParameterList(name, (Object[])value);
					}
				} else if( Collection.class.isAssignableFrom(value.getClass()) ){
					query.setParameterList(name, (Collection<?>)value);
				} else {
					query.setParameter(name, value);
				}
			}
		}
		return query;
	}
	
	/**
	 * 设置分页参数
	 * @param query
	 * @param pageable
	 * @return
	 */
	public static Query setPageParameter(Query query, Pageable pageable){
		if( notNull( pageable ) ){
			query.setFirstResult( (pageable.getPageNumber()-1) * pageable.getPageSize() );
			query.setMaxResults(pageable.getPageSize());
		}
		return query;
	}
	
	public static Criteria setPageParameter(Criteria criteria, Pageable pageable){
		if( notNull( pageable ) ){
			criteria.setFirstResult( (pageable.getPageNumber()-1) * pageable.getPageSize() );
			criteria.setMaxResults(pageable.getPageSize());
			setSortParameter(criteria, pageable.getSort());
		}
		return criteria;
	}

	public static Criteria setSortParameter(Criteria criteria, Sort sort){
		if( notNull( sort ) ){
			Iterator<Order> it = sort.getOrders().iterator();
			while( it.hasNext() ){
				criteria.addOrder(it.next());
			}
		}
		return criteria;
	}
	
	public static Query setResultTransformerType(Query query, Class<?> resultTransformerType){
		if( notNull( resultTransformerType ) ){
			if (Map.class == resultTransformerType) {
				return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			}
			if (List.class == resultTransformerType) {
				return query.setResultTransformer(Transformers.TO_LIST);
			} 
			return query.setResultTransformer(Transformers.TO_LIST);
		}
		return query;
	}
	
}
