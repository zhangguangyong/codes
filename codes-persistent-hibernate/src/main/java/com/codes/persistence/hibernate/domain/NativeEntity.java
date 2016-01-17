package com.codes.persistence.hibernate.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
/**
 * native主键生成策略
 * @author zhangguangyong
 *
 * 2015年10月27日 下午7:20:17
 */
@MappedSuperclass
public abstract class NativeEntity<ID extends Number> implements Entity<ID> {
	private ID id;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
	
	
}
