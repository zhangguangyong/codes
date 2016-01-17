package com.codes.persistence.hibernate.dao.impl;

import static com.codes.common.util.$.notNull;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.codes.persistence.hibernate.dao.HqlDao;
import com.codes.persistence.hibernate.dao.QueryParameterWrap;
import com.codes.persistence.hibernate.domain.Page;
import com.codes.persistence.hibernate.domain.PageImpl;
import com.codes.persistence.hibernate.domain.Pageable;

/**
 * Hibernate Dao Teamplate
 * 主要用于 Hibernate的 HQL 查询
 * @author zhangguangyong
 *
 * 2015年10月31日 下午3:27:43
 */
public class HqlTemplate implements HqlDao {

	protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected Query createQuery(String queryString) {
		return getCurrentSession().createQuery(queryString);
	}
	
	protected SQLQuery createSQLQuery(String queryString) {
		return getCurrentSession().createSQLQuery(queryString);
	}
	
	@Override
	public List<?> find(String ql) {
		return find(QueryParameterWrap.getInstance(ql));
	}

	@Override
	public List<?> find(String ql, Object arg0, Object... rest) {
		return find(QueryParameterWrap.getInstance(ql).addArgs(arg0, rest));
	}

	@Override
	public List<?> find(String ql, Object[] args) {
		return find(QueryParameterWrap.getInstance(ql).addArgs(args));
	}

	@Override
	public List<?> find(String ql, Map<String, Object> namedParameter) {
		return find(QueryParameterWrap.getInstance(ql).setNamedParameter(namedParameter));
	}

	@Override
	public List<?> find(QueryParameterWrap parameterWrap) {
		return DaoHelper.setParameter(createQuery(parameterWrap.getQueryString()), parameterWrap).list();
	}

	@Override
	public Page<?> findForPage(String ql, Pageable pageable) {
		return findForPage(QueryParameterWrap.getInstance(ql).setPageable(pageable));
	}

	@Override
	public Page<?> findForPage(String ql, Pageable pageable, Object arg0,
			Object... rest) {
		return findForPage(QueryParameterWrap.getInstance(ql).setPageable(pageable).addArgs(arg0, rest));
	}

	@Override
	public Page<?> findForPage(String ql, Pageable pageable, Object[] args) {
		return findForPage(QueryParameterWrap.getInstance(ql).setPageable(pageable).addArgs(args));
	}

	@Override
	public Page<?> findForPage(String ql, Pageable pageable,
			Map<String, Object> namedParameter) {
		return findForPage(QueryParameterWrap.getInstance(ql).setPageable(pageable).setNamedParameter(namedParameter));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<?> findForPage(QueryParameterWrap parameterWrap) {
		String queryString = parameterWrap.getQueryString();
		String countHql = DaoHelper.toCountHql(queryString);
		// 统计查询
		Query query = createQuery(countHql);
		Object totals = DaoHelper.setBasicParameter(query, parameterWrap).uniqueResult();
		if( notNull(totals) ){
			query = createQuery(queryString);
			List<?> rows = DaoHelper.setParameter(query, parameterWrap).list();
			return new PageImpl(Long.valueOf(totals.toString()), rows);
		}
		return null;
	}

	@Override
	public int update(String ql) {
		return update(QueryParameterWrap.getInstance(ql));
	}

	@Override
	public int update(String ql, Object[] args) {
		return update(QueryParameterWrap.getInstance(ql).addArgs(args));
	}

	@Override
	public int update(String ql, Object arg0, Object... rest) {
		return update(QueryParameterWrap.getInstance(ql).addArgs(arg0, rest));
	}

	@Override
	public int update(String ql, Map<String, Object> namedParameter) {
		return update(QueryParameterWrap.getInstance(ql).setNamedParameter(namedParameter));
	}

	@Override
	public int update(QueryParameterWrap parameterWrap) {
		return DaoHelper.setParameter(createQuery(parameterWrap.getQueryString()), parameterWrap).executeUpdate();
	}

}
