package com.codes.persistence.hibernate.domain;

import javax.persistence.MappedSuperclass;

/**
 * Integer类型native主键生成策略
 * @author zhangguangyong
 *
 * 2015年10月27日 下午7:20:42
 */
@MappedSuperclass
public abstract class IntegerNativeEntity extends NativeEntity<Integer> {
}
