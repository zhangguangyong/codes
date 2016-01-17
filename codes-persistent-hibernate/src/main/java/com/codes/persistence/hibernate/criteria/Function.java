package com.codes.persistence.hibernate.criteria;


/**
 * 使用函数统计，对应Hibernate 投影(Projections)、聚合（aggregation）和分组（grouping）
 * 
 * @author zhangguangyong
 *
 *         2015年11月3日 上午1:52:30
 */
public class Function {
	/** 别名连接符号 */
	static final String ALIAS_JOINT_SYMBOL = "_";

	/** 函数类型 */
	static enum FunctionType {
		count, min, max, countDistinct, rowCount, avg, groupProperty, sum
	}

	/** 函数名称 */
	static final String count = FunctionType.count.name();
	static final String countDistinct = FunctionType.countDistinct.name();
	static final String rowCount = FunctionType.rowCount.name();
	static final String avg = FunctionType.avg.name();
	static final String min = FunctionType.min.name();
	static final String max = FunctionType.max.name();
	static final String sum = FunctionType.max.name();
	static final String groupProperty = FunctionType.groupProperty.name();

	private String property;
	private String name;
	private String alias;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Function() {
	}

	public Function(String name) {
		this.name = name;
	}

	public Function(String name, String alias) {
		this.name = name;
		this.alias = alias;
	}

	public Function(String property, String name, String alias) {
		this.property = property;
		this.name = name;
		this.alias = alias;
	}

	public static Function rowCount() {
		return new Function(rowCount, rowCount);
	}

	public static Function count(String property) {
		return new Function(property, count, count + ALIAS_JOINT_SYMBOL
				+ property);
	}

	public static Function count(String property, String alias) {
		return new Function(property, count, alias);
	}

	public static Function countDistinct(String property) {
		return new Function(property, countDistinct, "countDistinct"
				+ ALIAS_JOINT_SYMBOL + property);
	}

	public static Function countDistinct(String property, String alias) {
		return new Function(property, countDistinct, alias);
	}

	public static Function max(String property) {
		return new Function(property, max, max + ALIAS_JOINT_SYMBOL + property);
	}

	public static Function max(String property, String alias) {
		return new Function(property, max, alias);
	}

	public static Function min(String property) {
		return new Function(property, min, min + ALIAS_JOINT_SYMBOL + property);
	}

	public static Function min(String property, String alias) {
		return new Function(property, min, alias);
	}

	public static Function avg(String property) {
		return new Function(property, avg, avg + ALIAS_JOINT_SYMBOL + property);
	}

	public static Function avg(String property, String alias) {
		return new Function(property, avg, alias);
	}

	public static Function sum(String property) {
		return new Function(property, sum, sum + ALIAS_JOINT_SYMBOL + property);
	}

	public static Function sum(String property, String alias) {
		return new Function(property, sum, alias);
	}

	public static Function groupProperty(String property) {
		return new Function(property, groupProperty, groupProperty
				+ ALIAS_JOINT_SYMBOL + property);
	}

	public static Function groupProperty(String property, String alias) {
		return new Function(property, groupProperty, alias);
	}

}
