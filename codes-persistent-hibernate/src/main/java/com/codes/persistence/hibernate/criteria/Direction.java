package com.codes.persistence.hibernate.criteria;

/**
 * 排序，对应Hibernate的Order
 * 
 * @author zhangguangyong
 *
 *         2015年11月3日 上午2:55:39
 */
public class Direction {
	/** 排序的属性 */
	private String property;

	/** 排序的方向 */
	private Boolean isAsc;

	public Direction() {
	}

	public Direction(String property, Boolean isAsc) {
		this.property = property;
		this.isAsc = isAsc;
	}

	public static Direction asc(String property) {
		return new Direction(property, true);
	}

	public static Direction desc(String property) {
		return new Direction(property, false);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Boolean getIsAsc() {
		return isAsc;
	}

	public void setIsAsc(Boolean isAsc) {
		this.isAsc = isAsc;
	}

}
