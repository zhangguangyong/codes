package com.codes.persistence.hibernate.dao.impl;

import static com.codes.common.util.$.isNull;
import static com.codes.common.util.$.notNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.codes.common.util.Reflections;
import com.codes.persistence.hibernate.dao.EntityDao;
import com.codes.persistence.hibernate.dao.impl.CurdDaoImpl.BatchType;
import com.codes.persistence.hibernate.domain.Entity;
import com.codes.persistence.hibernate.domain.Page;
import com.codes.persistence.hibernate.domain.PageImpl;
import com.codes.persistence.hibernate.domain.Pageable;
import com.codes.persistence.hibernate.domain.Sort;
import com.codes.persistence.hibernate.domain.support.Dateable;
import com.codes.persistence.hibernate.domain.support.ExtendField;
import com.codes.persistence.hibernate.domain.support.Movable;
import com.codes.persistence.hibernate.domain.support.Treeable;
import com.google.common.collect.Lists;

/**
 * 实体的数据访问操作
 * 
 * @author zhangguangyong
 *
 *         2015年10月28日 下午7:24:37
 */
@SuppressWarnings("all")
public class EntityTemplate implements EntityDao {

	// 批量处理的类型
	static enum BatchType {
		SAVE, UPDATE, SAVE_OR_UPDATE, DELETE
	}

	// 默认一次批量的个数
	static final int DEFAULT_BATCH_SIZE = 20;

	static final String HQL_NAMED_PARAMETER_IDS = "ids";

	static final String HQL_DELETE_ALL = "delete from %s";

	static final String HQL_DELETE_BY_ID = "delete from %s where %s = ?";

	static final String HQL_DELETE_BY_IDS = "delete from %s where %s in (:"
			+ HQL_NAMED_PARAMETER_IDS + ")";

	static final String HQL_QUERY_ALL = "from %s";

	static final String HQL_QUERY_BY_IDS = "from %s where %s in (:"
			+ HQL_NAMED_PARAMETER_IDS + ")";

	protected SessionFactory sessionFactory;

	@Override
	public <T> long count(Class<T> entityClass) {
		Object result = createCriteria(entityClass).setProjection(
				Projections.rowCount()).uniqueResult();
		if (notNull(result)) {
			return Long.valueOf(result.toString());
		}
		return 0;
	}

	@Override
	public <T> void delete(Class<T> entityClass, Serializable id) {
		createQuery(
				String.format(HQL_DELETE_BY_ID, entityClass.getName(),
						getIdFieldName(entityClass))).setParameter(0, id)
				.executeUpdate();
	}

	@Override
	public <T> void delete(Class<T> entityClass, Serializable[] ids) {
		delete(entityClass, Arrays.asList(ids));
	}

	@Override
	public <T> void delete(Class<T> entityClass, Iterable<?> ids) {
		createQuery(
				String.format(HQL_DELETE_BY_IDS, entityClass.getName(),
						getIdFieldName(entityClass))).setParameter(
				HQL_NAMED_PARAMETER_IDS, Lists.newArrayList(ids))
				.executeUpdate();
	}

	@Override
	public <T> void deleteAll(Class<T> entityClass) {
		createQuery(String.format(HQL_DELETE_ALL, entityClass.getName()))
				.executeUpdate();
	}

	@Override
	public <T> boolean exists(Class<T> entityClass, Serializable id) {
		return null != getCurrentSession().get(entityClass, id);
	}

	@Override
	public <T> Iterable<T> findAll(Class<T> entityClass) {
		return createQuery(String.format(HQL_QUERY_ALL, entityClass.getName()))
				.list();
	}

	@Override
	public <T, ID extends Serializable> Iterable<T> find(Class<T> entityClass, ID[] ids) {
		return find(entityClass, Arrays.asList(ids));
	}
	
	@Override
	public <T> Iterable<T> find(Class<T> entityClass, Iterable<?> ids) {
		return createQuery(
				String.format(HQL_QUERY_BY_IDS, entityClass.getName(),
						getIdFieldName(entityClass))).setParameter(
				HQL_NAMED_PARAMETER_IDS, Lists.newArrayList(ids)).list();
	}

	@Override
	public <T> T find(Class<T> entityClass, Serializable id) {
		return (T) getCurrentSession().get(entityClass, id);
	}

	@Override
	public void saveOrUpdate(Iterable<?> entities) {
		batchHandle(entities, BatchType.SAVE_OR_UPDATE);
	}

	@Override
	public Entity<? extends Serializable> saveOrUpdate(
			Entity<? extends Serializable> entity) {
		Session session = getCurrentSession();
		if (isNull(entity.getId())) {
			onBeforeSave(entity);
			session.save(entity);
			onAfterSave(entity);
		} else {
			onBeforeUpdate(entity);
			entity = (Entity<? extends Serializable>) session.merge(entity);
			onAfterUpdate(entity);
		}
		return entity;
	}

	@Override
	public <T> Page<T> find(Class<T> entityClass, Pageable pageable) {
		long count = count(entityClass);
		if (count > 0) {
			List content = DaoHelper.setPageParameter(
					createCriteria(entityClass), pageable).list();
			return new PageImpl<T>(count, content);
		}
		return null;
	}

	@Override
	public <T> Iterable<T> find(Class<T> entityClass, Sort sort) {
		return DaoHelper.setSortParameter(createCriteria(entityClass), sort)
				.list();
	}

	// 辅助借口-------------------------------------------------------------------

	/**
	 * 获取当前Session
	 * 
	 * @return
	 */
	protected Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	/**
	 * 获取ID名称
	 * 
	 * @param persistentClass
	 * @return
	 */
	protected String getIdFieldName(Class<?> persistentClass) {
		return getSessionFactory().getClassMetadata(persistentClass)
				.getIdentifierPropertyName();
	}

	/**
	 * 创建Criteria
	 * 
	 * @param persistentClass
	 * @return
	 */
	protected Criteria createCriteria(Class<?> persistentClass) {
		return getCurrentSession().createCriteria(persistentClass);
	}

	/**
	 * 创建Query
	 * 
	 * @param queryString
	 * @return
	 */
	protected Query createQuery(String queryString) {
		return getCurrentSession().createQuery(queryString);
	}

	/**
	 * 创建SQLQuery
	 * 
	 * @param queryString
	 * @return
	 */
	protected SQLQuery createSQLQuery(String queryString) {
		return getCurrentSession().createSQLQuery(queryString);
	}

	/**
	 * 获取SessionFactory
	 * 
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 设置SessionFactory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 批量操作
	 * 
	 * @param entities
	 * @param batchType
	 * @param batchSize
	 */
	protected void batchHandle(Iterable<?> entities, BatchType batchType,
			int batchSize) {
		Session session = getCurrentSession();
		Iterator<?> it = entities.iterator();
		int count = 0;
		while (it.hasNext()) {
			count++;
			Object entity = it.next();
			switch (batchType) {
			case SAVE:
				session.save(entity);
				break;
			case UPDATE:
				session.update(entity);
				break;
			case SAVE_OR_UPDATE:
				session.saveOrUpdate(entity);
				break;
			case DELETE:
				session.delete(entity);
				break;
			}
			if (count % batchSize == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	/**
	 * 批量处理
	 * 
	 * @param entities
	 * @param batchType
	 */
	protected void batchHandle(Iterable<?> entities, BatchType batchType) {
		batchHandle(entities, batchType, DEFAULT_BATCH_SIZE);
	}

	// -------------------------------------------------------------------

	/**
	 * 新增实体之前的处理
	 * 
	 * @param entity
	 * @return
	 */
	private Entity<? extends Serializable> onBeforeSave(
			Entity<? extends Serializable> entity) {
		Class<?> cls = entity.getClass();
		String indexName = DaoHelper.getExtendFieldName(cls, ExtendField.Index);
		String pathName = DaoHelper.getExtendFieldName(cls, ExtendField.Path);
		String parentName = DaoHelper.getExtendFieldName(cls,
				ExtendField.Parent);

		// 可记录时间的
		if (Dateable.class.isAssignableFrom(cls)) {
			Date now = Calendar.getInstance().getTime();
			((Dateable) entity).setCreateDate(now);
			((Dateable) entity).setLastModifiedDate(now);
		}

		// 可树形结构化的
		if (Treeable.class.isAssignableFrom(cls)) {
			Treeable self = ((Treeable) entity);
			// 获取上级节点
			Serializable parentId = self.getParent();

			// 设置节点自身的路径
			if (isNull(parentId)) {
				self.setPath("/"); // 没有上级节点的节点
				// 设置节点自身的排位
				Object maxIndex = createCriteria(cls)
						.add(Restrictions.eq(pathName, "/"))
						.setProjection(Projections.max(indexName))
						.uniqueResult();
				self.setIndex(isNull(maxIndex) ? 1 : Integer.valueOf(maxIndex
						.toString()) + 1);
			} else {
				// 设置节点自身的排位
				Object maxIndex = createCriteria(cls)
						.add(Restrictions.eq(parentName, parentId))
						.setProjection(Projections.max(indexName))
						.uniqueResult();
				self.setIndex(isNull(maxIndex) ? 1 : Integer.valueOf(maxIndex
						.toString()) + 1);
			}
		}
		// 可移动的
		else if (Movable.class.isAssignableFrom(cls)) {
			// 设置自身的排位
			Integer maxIndex = 1;
			Object maxVal = createCriteria(cls).setProjection(
					Projections.max(indexName)).uniqueResult();
			if (notNull(maxVal)) {
				maxIndex = Integer.valueOf(maxVal.toString()) + 1;
			}
			((Movable) entity).setIndex(maxIndex);
		}

		return entity;
	}

	/**
	 * 新增实体之后的处理
	 * 
	 * @param entity
	 * @return
	 */
	private Entity<? extends Serializable> onAfterSave(
			Entity<? extends Serializable> entity) {
		Class<?> cls = entity.getClass();
		if (Treeable.class.isAssignableFrom(cls)) {
			Treeable self = (Treeable) entity;
			if (notNull(self.getParent())) {
				Treeable parent = (Treeable) getCurrentSession().get(cls,
						self.getParent());
				self.setPath(parent.getPath() + entity.getId().toString() + "/");
				getCurrentSession().update(self);
			}
		}
		return entity;
	}

	/**
	 * 更新实体之前的处理
	 * 
	 * @param entity
	 * @return
	 */
	private Entity<? extends Serializable> onBeforeUpdate(
			Entity<? extends Serializable> entity) {
		// 更新前的操作
		Class<?> cls = entity.getClass();
		String entityName = cls.getName();
		String idFieldName = getIdFieldName(cls);
		Serializable id = entity.getId();

		String indexName = DaoHelper.getExtendFieldName(cls, ExtendField.Index);
		String pathName = DaoHelper.getExtendFieldName(cls, ExtendField.Path);
		String parentName = DaoHelper.getExtendFieldName(cls,
				ExtendField.Parent);

		System.out.println(indexName + "," + pathName + "," + parentName);

		// 可记录时间的
		if (Dateable.class.isAssignableFrom(cls)) {
			((Dateable) entity).setLastModifiedDate(Calendar.getInstance()
					.getTime());
		}

		// 可树形结构化的
		if (Treeable.class.isAssignableFrom(cls)) {
			// 判断自身位置有没有发生变化
			Treeable currObject = (Treeable) entity;
			Serializable newParentId = currObject.getParent();
			Treeable dbObject = (Treeable) getCurrentSession().get(cls, id);
			Serializable oldParentId = dbObject.getParent();

			System.out.println("newParentId:" + newParentId
					+ " --> oldParentId:" + oldParentId);

			// 跨层级移动
			if (notNull(newParentId) && (!newParentId.equals(oldParentId))) {
				String oldPath = dbObject.getPath();
				Treeable newParent = (Treeable) getCurrentSession().get(cls,
						newParentId);
				String newPath = newParent.getPath() + id.toString() + "/";
				currObject.setPath(newPath);

				System.out.println("newPath:" + newPath + " --> oldPath:"
						+ oldPath);

				// 1.修改自身的排位
				Object maxVal = createCriteria(cls)
						.add(Restrictions.eq(parentName, currObject.getParent()))
						.setProjection(Projections.max(indexName))
						.uniqueResult();
				currObject.setIndex(isNull(maxVal) ? 1 : (Integer
						.valueOf(maxVal.toString()) + 1));
				// 2.如果自身的下级节点路劲：截取dbObject的path 换成 currentObject的path
				int subStart = oldPath.length() + 1;
				String hql = "update " + entityName + " set " + pathName
						+ " = Concat(?, Substring( " + pathName + ", "
						+ subStart + ", Length(" + pathName + ") )) where "
						+ pathName + " like ?";
				getCurrentSession().createQuery(hql).setParameter(0, newPath)
						.setParameter(1, oldPath + "_%").executeUpdate();
			}
			// 同级之间移动
			else if (!dbObject.getIndex().equals(currObject.getIndex())) {
				// 更新目标节点的位置
				String hql = "update " + entityName + " set " + indexName
						+ "=? where " + parentName + "=? and " + indexName
						+ "=?";
				createQuery(hql).setParameter(0, dbObject.getIndex())
						.setParameter(1, dbObject.getParent())
						.setParameter(2, currObject.getIndex()).executeUpdate();
			}

		}
		// 可移动的
		else if (Movable.class.isAssignableFrom(cls)) {
			Movable currObject = (Movable) entity;
			Movable dbObject = (Movable) getCurrentSession().get(cls,
					entity.getId());
			if (!dbObject.getIndex().equals(currObject.getIndex())) {
				String hql = "update " + entityName + " set " + indexName
						+ "=? where " + indexName + "=? ";
				createQuery(hql).setParameter(0, currObject.getIndex())
						.setParameter(1, dbObject.getIndex()).executeUpdate();
			}
		}

		return entity;
	}

	/**
	 * 更新实体之后的处理
	 * 
	 * @param entity
	 */
	private void onAfterUpdate(Entity<? extends Serializable> entity) {
		// TODO 更新实体之后的处理(暂无)
	}

}
