package com.codes.persistence.hibernate.domain.support;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.codes.persistence.hibernate.domain.IntegerNativeEntity;

/**
 * 默认的被支持的实体
 * @author Administrator
 *
 * 2015年10月27日 下午4:28:11
 */
@MappedSuperclass
public abstract class DefaultSupportEntity extends IntegerNativeEntity implements Dateable, Movable<Integer> {
	
	@ExtendFieldAnno(ExtendField.Index)
	protected Integer index;
	@ExtendFieldAnno(ExtendField.CreateData)
	protected Date createDate;
	@ExtendFieldAnno(ExtendField.LastModifiedDate)
	protected Date lastModifiedDate;
	
	@Override
	@Column(name="ext_index")
	public Integer getIndex() {
		return this.index;
	}

	@Override
	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	@Column(name="ext_createDate")
	public Date getCreateDate() {
		return this.createDate;
	}

	@Override
	@Column(name="ext_lastModifiedDate")
	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
}
