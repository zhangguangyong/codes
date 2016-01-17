package com.codes.platform.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.codes.persistence.hibernate.domain.support.DefaultSupportEntity;

/**
 * 字典实体类
 * 
 * @author zhangguangyong
 *
 *         2015年11月9日 上午12:56:27
 */
@Entity
@Table(name = "tb_system_Dictionary")
public class Dictionary extends DefaultSupportEntity{
	private String name;		//名称
	private String code;		//编码
	private String category;	//类别
	
	@Column
	public String getName() {
		return name;
	}
	
	@Column
	public String getCode() {
		return code;
	}
	
	@Column
	public String getCategory() {
		return category;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
	
}
