package com.codes.persistence.hibernate.dao.impl;

import static com.codes.common.util.$.notNull;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

import com.codes.persistence.hibernate.criteria.Condition;
import com.codes.persistence.hibernate.criteria.CriteriaHelper;
import com.codes.persistence.hibernate.dao.ConditionDao;
import com.codes.persistence.hibernate.domain.Page;
import com.codes.persistence.hibernate.domain.PageImpl;
import com.codes.persistence.hibernate.domain.Pageable;
import com.codes.persistence.hibernate.domain.Sort;

/**
 * 根据属性搜索的模板
 * @author zhangguangyong
 *
 * 2015年10月31日 下午4:35:07
 */
public class ConditionTemplate implements ConditionDao{
	
	protected SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession(){
		return getSessionFactory().getCurrentSession();
	}
	
	public Criteria createCriteria(Class<?> persistentClass){
		return getCurrentSession().createCriteria(persistentClass);
	}
	
	@Override
	public List<?> find(Condition condition, Class<?> persistentClass) {
		return find(condition, persistentClass, (Sort)null);
	}

	@Override
	public List<?> find(Condition condition, Class<?> persistentClass, Sort sort) {
		Criteria criteria = createCriteria(persistentClass);
		CriteriaHelper.parseConditionToCriteria(condition, criteria);
		DaoHelper.setSortParameter(criteria, sort);
		return criteria.list();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<?> find(Condition condition, Class<?> persistentClass,
			Pageable pageable) {
		Criteria criteria = createCriteria(persistentClass);
		CriteriaHelper.parseConditionToCriteria(condition, criteria);
		
		Object rowCount = criteria.setProjection(Projections.rowCount()).uniqueResult();
		if( notNull(rowCount) ){
			long totalRows = Long.valueOf(rowCount.toString());
			criteria.setProjection(null);
			DaoHelper.setPageParameter(criteria, pageable);
			return new PageImpl(totalRows, criteria.list());
		}
		return null;
	}

}
