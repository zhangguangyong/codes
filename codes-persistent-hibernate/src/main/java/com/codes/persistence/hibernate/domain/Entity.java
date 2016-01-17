package com.codes.persistence.hibernate.domain;

import java.io.Serializable;
/**
 * 持久化实体标准
 * @author zhangguangyong
 *
 * 2015年10月27日 下午7:19:53
 */
public interface Entity<ID extends Serializable> {
	ID getId();
}
