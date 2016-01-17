package com.codes.persistence.hibernate.dao.impl;

import static com.codes.common.util.$.notEmpty;
import static com.codes.common.util.$.notNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;

import com.codes.persistence.hibernate.dao.QueryParameterWrap;
import com.codes.persistence.hibernate.dao.SqlDao;
import com.codes.persistence.hibernate.dao.SqlQueryParameterWrap;
import com.codes.persistence.hibernate.domain.Page;
import com.codes.persistence.hibernate.domain.PageImpl;
import com.codes.persistence.hibernate.domain.Pageable;
import com.google.common.collect.Lists;

/**
 * SQL Dao 接口实现
 * @author zhangguangyong
 *
 * 2015年10月30日 下午8:40:55
 */
@SuppressWarnings("all")
public class SqlTemplate extends HqlTemplate implements SqlDao {
	public static final String SINGLE_COLUMN_ALIAS = "single_column_alias";
	
	@Override
	public List<?> find(QueryParameterWrap parameterWrap) {
		parameterWrap.setResultTransformerType(Map.class);
		SQLQuery query = createSQLQuery(parameterWrap.getQueryString());
		return DaoHelper.setParameter(query, parameterWrap).list();
	}
	
	@Override
	public Page<?> findForPage(QueryParameterWrap parameterWrap) {
		String queryString = parameterWrap.getQueryString();
		String countSql = DaoHelper.toCountSql(queryString);
		// 统计查询
		SQLQuery query = createSQLQuery(countSql);
		Object totals = DaoHelper.setBasicParameter(query, parameterWrap).uniqueResult();
		if( notNull(totals) ){
			query = createSQLQuery(queryString);
			List rows = DaoHelper.setParameter(query, parameterWrap).list();
			return new PageImpl(Long.valueOf(totals.toString()), rows);
		}
		return null;
	}

	@Override
	public <T> T findForObject(String sql, Class<T> requiredType) {
		return findForObject(createSQLQuery(sql), requiredType);
	}

	@Override
	public <T> T findForObject(String sql, Class<T> requiredType, Object arg0,
			Object... rest) {
		return findForObject( DaoHelper.setParameter(createSQLQuery(sql), Lists.asList(arg0, rest)) , requiredType);
	}
	
	@Override
	public <T> T findForObject(String sql, Class<T> requiredType, Object[] args) {
		return findForObject( DaoHelper.setParameter(createSQLQuery(sql), args) , requiredType);
	}
	
	@Override
	public <T> T findForObject(String sql, Class<T> requiredType, Map<String, Object> namedParameter) {
		return findForObject( DaoHelper.setParameter(createSQLQuery(sql), namedParameter) , requiredType);
	}
	
	private <T> T findForObject(Query query, Class<T> requiredType) {
		Object obj = query.uniqueResult();
		if( notNull(obj) ){
			try {
				Class<? extends Object> objClass = obj.getClass();
				// 返回结果是BigDecimal 或  BigInteger 类型，则直接转换为Long类型
				if( BigDecimal.class.isAssignableFrom(objClass) || BigInteger.class.isAssignableFrom(objClass) ){
					return (T) requiredType.getMethod("longValue").invoke(obj);
				}
				
				// 如果是Number类型，则调用转换类型的valueOf方法进行转换
				if( Number.class.isAssignableFrom(requiredType) ){
					return (T) requiredType.getMethod("valueOf", String.class).invoke(null, obj.toString());
				}
				
				// 其他则直接返回
				return (T) obj;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> findForMap(String sql) {
		return findForMap(SqlQueryParameterWrap.getInstance(sql));
	}

	@Override
	public Map<String, Object> findForMap(String sql, Object arg0,
			Object... rest) {
		return findForMap(SqlQueryParameterWrap.getInstance(sql).addArgs(arg0, rest));
	}
	
	@Override
	public Map<String, Object> findForMap(String sql, Object[] args) {
		return findForMap(SqlQueryParameterWrap.getInstance(sql).addArgs(args));
	}

	@Override
	public Map<String, Object> findForMap(String sql,
			Map<String, Object> namedParameter) {
		return findForMap(SqlQueryParameterWrap.getInstance(sql).setNamedParameter(namedParameter));
	}
	
	public Map<String, Object> findForMap(SqlQueryParameterWrap parameterWrap) {
		parameterWrap.setResultTransformerType(Map.class);
		SQLQuery query = createSQLQuery( parameterWrap.getQueryString() );
		DaoHelper.setParameter(query, parameterWrap);
		List<Map> list = query.list();
		if( notEmpty(list) ){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public <T> List<T> findForList(String sql, Class<T> elementType) {
		return findForList(SqlQueryParameterWrap.getInstance(sql), elementType);
	}

	@Override
	public <T> List<T> findForList(String sql, Class<T> elementType,
			Object[] args) {
		return findForList(SqlQueryParameterWrap.getInstance(sql).addArgs(args), elementType);
	}

	@Override
	public <T> List<T> findForList(String sql, Class<T> elementType,
			Object arg0, Object... rest) {
		return findForList(SqlQueryParameterWrap.getInstance(sql).addArgs(arg0, rest), elementType);
	}

	@Override
	public <T> List<T> findForList(String sql, Class<T> elementType,
			Map<String, Object> namedParameter) {
		return findForList(SqlQueryParameterWrap.getInstance(sql).setNamedParameter(namedParameter), elementType);
	}
	
	public <T> List<T> findForList(SqlQueryParameterWrap parameterWrap, Class<T> elementType){
		String singleColumnSql = DaoHelper.toSingleColumnSql( parameterWrap.getQueryString() );
		SQLQuery query = createSQLQuery( singleColumnSql );
		parameterWrap.addScalar(SINGLE_COLUMN_ALIAS, elementType);
		DaoHelper.setParameter(query, parameterWrap);
		return query.list();
	}
			
	@Override
	public int update(QueryParameterWrap parameterWrap) {
		SQLQuery query = createSQLQuery( parameterWrap.getQueryString() );
		return DaoHelper.setParameter(query, parameterWrap).executeUpdate();
	}
	
	@Override
	public int[] batchUpdate(final String sql, final List<Object[]> args) {
		return getCurrentSession().doReturningWork(new ReturningWork<int[]>() {
			@Override
			public int[] execute(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql);
				for (Object[] arg : args) {
					for (int i = 0; i < arg.length; i++) {
						ps.setObject(i + 1, arg[i]);
					}
					ps.addBatch();
				}
				return ps.executeBatch();
			}
		});
	}

	@Override
	public int[] batchUpdate(final String... sqls) {
		return getCurrentSession().doReturningWork(new ReturningWork<int[]>() {
			@Override
			public int[] execute(Connection connection) throws SQLException {
				Statement st = connection.createStatement();
				for (String sql : sqls) {
					st.addBatch(sql);
				}
				return st.executeBatch();
			}
		});
	}

	public static void main(String[] args) {
		Object o = 4;
		Object[] arr = {3,3,3};
		System.out.println( Lists.asList(o, arr) );
	}
}
