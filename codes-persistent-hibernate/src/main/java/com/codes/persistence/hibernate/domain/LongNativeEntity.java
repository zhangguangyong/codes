package com.codes.persistence.hibernate.domain;

import javax.persistence.MappedSuperclass;

/**
 * Long类型native主键生成策略
 * @author zhangguangyong
 *
 * 2015年10月27日 下午7:21:04
 */
@MappedSuperclass
public abstract class LongNativeEntity extends NativeEntity<Long> {
}
