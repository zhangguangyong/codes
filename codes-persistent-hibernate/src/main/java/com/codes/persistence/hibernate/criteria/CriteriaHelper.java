package com.codes.persistence.hibernate.criteria;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;

/**
 * 条件辅助类
 * 
 * @author zhangguangyong
 *
 *         2015年10月27日 下午9:32:49
 */
public class CriteriaHelper {

	public static Criteria parseConditionToCriteria(Condition condition,
			Criteria criteria) {
		if (!(criteria instanceof CriteriaImpl)) {
			throw new IllegalArgumentException("parameter" + criteria
					+ " must be " + CriteriaImpl.class + " type.");
		}
		String entityOrClassName = null;
		try {
			entityOrClassName = ((CriteriaImpl) criteria)
					.getEntityOrClassName();
			Class<?> persistentClass = Class.forName(entityOrClassName);
			return parseConditionToCriteria(condition, criteria,
					persistentClass);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(
					"entity name is not a full class name: "
							+ entityOrClassName);
		}
	}

	/**
	 * 解析一个条件成为符合Hibernate的Criteria的实例条件
	 * 
	 * @param condition
	 * @param criteria
	 * @return
	 */
	public static Criteria parseConditionToCriteria(Condition condition,
			Criteria criteria, Class<?> persistentClass) {
		// 创建一个Or的连接条件集合
		Disjunction disjunction = Restrictions.disjunction();
		parseConditionToJunction(condition, disjunction, persistentClass);
		return criteria.add(disjunction);
	}

	/**
	 * 解析一个条件成为符合Hibernate的Junction的实例条件
	 * 
	 * @param condition
	 * @param junction
	 */
	public static void parseConditionToJunction(Condition condition,
			Junction junction, Class<?> persistentClass) {
		// 子条件
		List<Condition> subConditions = condition.getSubConditions();

		// 有子条件则进行遍历获取最终的条件
		if (null != subConditions && !subConditions.isEmpty()) {
			// 子条件当中的and条件集合
			List<Condition> andConditions = condition.getAndConditions();
			if (null != andConditions && !andConditions.isEmpty()) {
				// 创建一个and条件集合
				Conjunction conj = Restrictions.conjunction();
				// 添加到父条件中
				junction.add(conj);
				for (Condition andCondition : andConditions) {
					/*
					 * 把每个条件看做是一个大的条件(包含 and 和 or 2个部分),
					 * 然后使用disjunction连接条件集合来组合这个大条件的(and项 和 or项);
					 * 因为and项和or项都已经被分为2个部分
					 * ,而且又是用disjunction来组合,所以可以保证一个大条件只会被or连接符分隔成2个部分
					 */
					Disjunction dj = Restrictions.disjunction();
					conj.add(dj);
					parseConditionToJunction(andCondition, dj, persistentClass);
				}
			}

			// 子条件当中的or条件集合
			List<Condition> orConditions = condition.getOrConditions();
			if (null != orConditions && !orConditions.isEmpty()) {
				// 创建一个or条件集合
				Disjunction disj = Restrictions.disjunction();
				// 添加到父条件中
				junction.add(disj);
				for (Condition orCondition : orConditions) {
					// 这里的实现原理与上面的andCondition的处理相同
					Disjunction dj = Restrictions.disjunction();
					disj.add(dj);
					parseConditionToJunction(orCondition, dj, persistentClass);
				}
			}
		} else {
			// 条件为最终条件，转换为符合Hibernate的条件，然后条件到条件集合中
			junction.add(condition.getCriterion(persistentClass));
		}
	}

}
