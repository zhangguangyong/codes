package com.codes.platform.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.codes.persistence.hibernate.domain.IntegerNativeEntity;

/**
 * 搜索条件
 * 
 * @author zhangguangyong
 *
 *         2015年11月5日 下午5:07:42
 */

@Entity
@Table(name = "tb_system_SearchCondition")
public class SearchCondition extends IntegerNativeEntity {
	/** 前置条件 */
	private String preconditions;
	
	/** 条件 */
	private String conditions;
	
	/** 属性条件分组 */
	private String conditionsGroup;

	/** 排序条件 */
	private String orders;

	/** 分页条件 */
	private String pages;

	/** 对应的实体名称 */
	private String entityName;

	@Column
	public String getPreconditions() {
		return preconditions;
	}
	
	@Column(length=4000)
	public String getConditions() {
		return conditions;
	}

	@Column
	public String getConditionsGroup() {
		return conditionsGroup;
	}

	@Column
	public String getOrders() {
		return orders;
	}

	@Column
	public String getPages() {
		return pages;
	}

	@Column
	public String getEntityName() {
		return entityName;
	}

	public void setPreconditions(String preconditions) {
		this.preconditions = preconditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public void setConditionsGroup(String conditionsGroup) {
		this.conditionsGroup = conditionsGroup;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	

	

}
