package com.codes.persistence.hibernate.domain.support;

import java.io.Serializable;

/**
 * 可树形结构化
 * 
 * @author zhangguangyong
 *
 *         2015年10月27日 下午7:22:41
 */
public interface Treeable<P extends Serializable, I> extends Movable<I> {

	/**
	 * 获取上级节点
	 * 
	 * @return
	 */
	P getParent();

	/**
	 * 设置上级节点
	 * 
	 * @param p
	 */
	void setParent(P p);

	/**
	 * 获取自身所在位置：a/b/c
	 * 
	 * @return
	 */
	String getPath();

	/**
	 * 设置自身所有在位置: a/b/c
	 * 
	 * @param position
	 */
	void setPath(String path);

}
