package com.codes.persistence.hibernate.criteria;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.codes.common.util.Reflections;

/**
 * 条件查询,对应Hibernate的Restriction接口
 * 
 * @author zhangguangyong
 *
 *         2015年11月3日 上午1:47:42
 */
public class Condition {

	private String joinType; // 连接类型(and,or)
	private String property; // 条件列,实体的属性名称
	private String expression; // 表达式
	private Object value; // 条件值,实体的属性值

	private List<Condition> subConditions; // 子条件集

	static final String AND = "and";
	static final String OR = "or";

	public static Condition getInstanceAnd() {
		return new Condition(AND);
	}

	public static Condition getInstanceOr() {
		return new Condition(OR);
	}

	private Condition(String joinType) {
		this.joinType = joinType;
	}

	private Condition(String joinType, String property, String expression, Object value) {
		this.joinType = joinType;
		this.property = property;
		this.expression = expression;
		this.value = value;
	}

	public Condition addSub(Condition... conditions) {
		return addSub(Arrays.asList(conditions));
	}

	public Condition addSub(Iterable<Condition> conditions) {
		if (null == subConditions) {
			subConditions = new ArrayList<Condition>();
		}
		Iterator<Condition> itera = conditions.iterator();
		while (itera.hasNext()) {
			subConditions.add(itera.next());
		}
		return this;
	}

	public Condition and(String property, String expression, Object value) {
		if (null == subConditions) {
			subConditions = new ArrayList<Condition>();
		}
		subConditions.add(new Condition(AND, property, expression, value));
		return this;
	}

	public Condition or(String property, String expression, Object value) {
		if (null == subConditions) {
			subConditions = new ArrayList<Condition>();
		}
		subConditions.add(new Condition(OR, property, expression, value));
		return this;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public List<Condition> getSubConditions() {
		return subConditions;
	}

	public void setSubConditions(List<Condition> subConditions) {
		this.subConditions = subConditions;
	}

	/**
	 * 判断是不是 and 条件
	 * 
	 * @return
	 */
	public boolean isAndCondition() {
		return AND.equals(joinType);
	}

	/**
	 * 获取所有的 AND 子条件
	 * 
	 * @return
	 */
	public List<Condition> getAndConditions() {
		if (null == subConditions) {
			return null;
		}
		List<Condition> ret = new ArrayList<Condition>();
		for (Condition subContion : subConditions) {
			if (AND.equals(subContion.joinType)) {
				ret.add(subContion);
			}
		}
		return ret;
	}

	/**
	 * 获取所有的 OR 子条件
	 * 
	 * @return
	 */
	public List<Condition> getOrConditions() {
		if (null == subConditions) {
			return null;
		}
		List<Condition> ret = new ArrayList<Condition>();
		for (Condition subContion : subConditions) {
			if (!AND.equals(subContion.joinType)) {
				ret.add(subContion);
			}
		}
		return ret;
	}

	/**
	 * 获取符合Hibernate的条件
	 * 
	 * @return
	 */
	public Criterion getCriterion(Class<?> persistentClass) {
		Expression exp = Expression.valueOf(expression);

		// 表达式为：in, between (输入值为多个值)
		List<Expression> multivalueExpres = Arrays.asList(Expression.in, Expression.nin, Expression.between,
				Expression.nbetween);
		if (multivalueExpres.contains(exp)) {
			Object[] vals = null;
			Class<? extends Object> vType = value.getClass();
			if (vType.isArray()) {
				// value是数组类型
				vals = (Object[]) value;
			} else if (Collection.class.isAssignableFrom(vType)) {
				// value是集合类型
				vals = ((Collection<?>) value).toArray();
			} else if (String.class.isAssignableFrom(vType)) {
				// value值字符串类型, 需要判断属性的类型, 来包装
				String[] strs = value.toString().split(",");
				Class<?> fieldType = Reflections.getDeclaredField(persistentClass, property).getType();
				if (String.class.isAssignableFrom(fieldType)) {
					vals = strs;
				}
				try {
					if (Number.class.isAssignableFrom(fieldType)) {
						Method m = fieldType.getMethod("valueOf", String.class);
						vals = new Object[strs.length];
						for (int i = 0; i < strs.length; i++) {
							vals[i] = m.invoke(null, strs[i].trim());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null == vals) {
				throw new RuntimeException("不支持的字段解析类型：" + vType + ", 对于表达式:" + exp + "的输入值");
			}

			switch (exp) {
			case in:
				return Restrictions.in(property, vals);
			case nin:
				return Restrictions.not(Restrictions.in(property, vals));
			case between:
				return Restrictions.between(property, vals[0], vals[1]);
			case nbetween:
				return Restrictions.not(Restrictions.between(property, vals[0], vals[1]));
			default:
				return null;
			}
		}

		// 表达式为：isNull, isEmpty, isNotNull, isNotEmpty, isNullOrIsEmpty (没有输入值)
		List<Expression> nullvalueExpres = Arrays.asList(Expression.isNull, Expression.isNotNull, Expression.isEmpty,
				Expression.isNotEmpty, Expression.isNullOrIsEmpty);
		if (nullvalueExpres.contains(exp)) {
			switch (exp) {
			case isNull:
				return Restrictions.isNull(property);
			case isNotNull:
				return Restrictions.isNotNull(property);
			case isEmpty:
				return Restrictions.isEmpty(property);
			case isNotEmpty:
				return Restrictions.isNotEmpty(property);
			case isNullOrIsEmpty:
				return Restrictions.or(Restrictions.isNull(property), Restrictions.isEmpty(property));
			default:
				return null;
			}
		}

		// 获取属性的真实值
		Object fieldValue = getFieldValue(persistentClass, property, value.toString());
		String fieldStringValue = fieldValue.toString();

		switch (exp) {
		// 等于，不等于，大于，大于等于，小于，小于等于 (当使用属性比较时, fieldStringValue 代表的就是另外一个属性了)
		case eq:
			return Restrictions.eq(property, fieldValue);
		case eqProperty:
			return Restrictions.eqProperty(property, fieldStringValue);

		case eqOrIsNull:
			return Restrictions.eqOrIsNull(property, fieldValue);

		case ne:
			return Restrictions.ne(property, fieldValue);
		case neProperty:
			return Restrictions.neProperty(property, fieldStringValue);

		case neOrIsNotNull:
			return Restrictions.neOrIsNotNull(property, fieldValue);

		case gt:
			return Restrictions.gt(property, fieldValue);
		case gtProperty:
			return Restrictions.gtProperty(property, fieldStringValue);

		case ge:
			return Restrictions.ge(property, fieldValue);
		case geProperty:
			return Restrictions.geProperty(property, fieldStringValue);

		case lt:
			return Restrictions.lt(property, fieldValue);
		case ltProperty:
			return Restrictions.ltProperty(property, fieldStringValue);

		case le:
			return Restrictions.le(property, fieldValue);
		case leProperty:
			return Restrictions.leProperty(property, fieldStringValue);

		// id等于
		case idEq:
			return Restrictions.idEq(fieldValue);

		// 属性非空判断
		case isEmpty:
			return Restrictions.isEmpty(property);
		case isNotEmpty:
			return Restrictions.isNotEmpty(property);
		case isNull:
			return Restrictions.isNull(property);
		case isNotNull:
			return Restrictions.isNotNull(property);

		// like, ilike
		case like:
			return Restrictions.like(property, fieldValue);
		case alike:
			return Restrictions.like(property, fieldStringValue, MatchMode.ANYWHERE);
		case slike:
			return Restrictions.like(property, fieldStringValue, MatchMode.START);
		case elike:
			return Restrictions.like(property, fieldStringValue, MatchMode.END);
		case exlike:
			return Restrictions.like(property, fieldStringValue, MatchMode.EXACT);

		case nlike:
			return Restrictions.not(Restrictions.like(property, fieldValue));
		case nalike:
			return Restrictions.not(Restrictions.like(property, fieldStringValue, MatchMode.ANYWHERE));
		case nslike:
			return Restrictions.not(Restrictions.like(property, fieldStringValue, MatchMode.START));
		case nelike:
			return Restrictions.not(Restrictions.like(property, fieldStringValue, MatchMode.END));
		case nexlike:
			return Restrictions.not(Restrictions.like(property, fieldStringValue, MatchMode.EXACT));

		case ilike:
			return Restrictions.ilike(property, fieldValue);
		case ailike:
			return Restrictions.ilike(property, fieldStringValue, MatchMode.ANYWHERE);
		case silike:
			return Restrictions.ilike(property, fieldStringValue, MatchMode.START);
		case eilike:
			return Restrictions.ilike(property, fieldStringValue, MatchMode.END);
		case exilike:
			return Restrictions.ilike(property, fieldStringValue, MatchMode.EXACT);

		case nilike:
			return Restrictions.not(Restrictions.ilike(property, fieldValue));
		case nailike:
			return Restrictions.not(Restrictions.ilike(property, fieldStringValue, MatchMode.ANYWHERE));
		case nsilike:
			return Restrictions.not(Restrictions.ilike(property, fieldStringValue, MatchMode.START));
		case neilike:
			return Restrictions.not(Restrictions.ilike(property, fieldStringValue, MatchMode.END));
		case nexilike:
			return Restrictions.not(Restrictions.ilike(property, fieldStringValue, MatchMode.EXACT));

		default:
			return null;
		}
	}

	/**
	 * 获取属性的真实值
	 * 
	 * @param cls
	 * @param fieldName
	 * @param strValue
	 * @return
	 */
	private static Object getFieldValue(Class<?> cls, String fieldName, String strValue) {
		try {
			Class<?> type = Reflections.getDeclaredField(cls, fieldName).getType();
			// 是数字类型
			if (Number.class.isAssignableFrom(type)) {
				return type.getMethod("valueOf", String.class).invoke(null, strValue);
			}
			// 字符类型
			if (Character.class.isAssignableFrom(type)) {
				return strValue.charAt(0);
			}
			// 字符串类型
			if (String.class.isAssignableFrom(type)) {
				return strValue;
			}
			// 日期类型
			if (Date.class.isAssignableFrom(type)) {
				// TODO 日期类型处理
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
