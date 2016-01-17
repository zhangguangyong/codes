package com.codes.persistence.hibernate.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * UUID主键生成策略
 * @author zhangguangyong
 *
 * 2015年10月27日 下午7:21:29
 */
@MappedSuperclass
public abstract class UuidEntity implements Entity<String> {
	private String id;
	
	@Override
	@Id
	@GeneratedValue(generator = "uuidGenerator")  
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
