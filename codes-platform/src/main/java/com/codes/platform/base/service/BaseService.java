package com.codes.platform.base.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.codes.persistence.hibernate.dao.ConditionDao;
import com.codes.persistence.hibernate.dao.EntityDao;
import com.codes.persistence.hibernate.dao.HqlDao;
import com.codes.persistence.hibernate.dao.SqlDao;

/**
 * 基础Service
 * 
 * @author zhangguangyong
 *
 *         2015年11月5日 下午3:50:56
 */
@org.springframework.stereotype.Service
public class BaseService implements Service {
	private SqlDao sqlDao;

	private HqlDao hqlDao;

	private EntityDao entityDao;

	private ConditionDao conditionDao;

	public SqlDao getSqlDao() {
		return sqlDao;
	}

	public HqlDao getHqlDao() {
		return hqlDao;
	}

	public ConditionDao getConditionDao() {
		return conditionDao;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	@Autowired
	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

	@Autowired
	public void setHqlDao(HqlDao hqlDao) {
		this.hqlDao = hqlDao;
	}

	@Autowired
	public void setConditionDao(ConditionDao conditionDao) {
		this.conditionDao = conditionDao;
	}

	@Autowired
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
