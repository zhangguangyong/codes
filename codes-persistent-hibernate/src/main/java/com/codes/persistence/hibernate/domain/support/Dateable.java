package com.codes.persistence.hibernate.domain.support;

import java.util.Date;

/**
 * 可设置日期的
 * 
 * @author zhangguangyong
 *
 *         2015年10月27日 下午7:22:16
 */
public interface Dateable {
	/**
	 * 获取创建时间
	 * 
	 * @return
	 */
	Date getCreateDate();

	/**
	 * 获取后一次修改时间
	 * 
	 * @return
	 */
	Date getLastModifiedDate();

	/**
	 * 设置创建时间
	 * 
	 * @param createDate
	 */
	void setCreateDate(Date createDate);

	/**
	 * 设置后一次修改时间
	 * 
	 * @param lastModifiedDate
	 */
	void setLastModifiedDate(Date lastModifiedDate);

}
