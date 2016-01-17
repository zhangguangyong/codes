package com.codes.persistence.hibernate.domain.support;

/**
 * 可审核的
 * 
 * @author zhangguangyong
 *
 *         2015年10月27日 下午7:21:57
 */
public interface Auditable<U> extends Dateable {

	/**
	 * 获取创建人
	 * 
	 * @return
	 */
	U getCreateBy();

	/**
	 * 获取最有一次修改人
	 * 
	 * @return
	 */
	U getLastModifiedBy();

	/**
	 * 设置创建人
	 * 
	 * @param createBy
	 */
	void setCreateBy(U createBy);

	/**
	 * 设置最后一次修改人
	 * 
	 * @param lastModifiedBy
	 */
	void setLastModifiedBy(U lastModifiedBy);

}
