package com.codes.persistence.hibernate.domain.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 扩展字段注解
 * @author zhangguangyong
 *
 * 2015年10月28日 下午8:44:19
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExtendFieldAnno {
	ExtendField value();
}
