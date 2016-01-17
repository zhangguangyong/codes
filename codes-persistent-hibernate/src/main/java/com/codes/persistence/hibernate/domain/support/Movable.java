package com.codes.persistence.hibernate.domain.support;

/**
 * 可移动的
 * 
 * @author zhangguangyong
 *
 *         2015年10月27日 下午7:22:29
 */
public interface Movable<I> {

	/**
	 * 获取所在位置索引
	 * 
	 * @return
	 */
	I getIndex();

	/**
	 * 设置所在位置索引
	 * 
	 * @param index
	 */
	void setIndex(I index);
}
