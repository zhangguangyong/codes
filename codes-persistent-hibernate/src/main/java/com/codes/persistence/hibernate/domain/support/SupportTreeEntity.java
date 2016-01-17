package com.codes.persistence.hibernate.domain.support;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
/**
 * 支持树形结构化的实体
 * @author Administrator
 *
 * 2015年10月27日 下午4:27:29
 */
@MappedSuperclass
public abstract class SupportTreeEntity extends DefaultSupportEntity implements Treeable<Integer, Integer> {
	@ExtendFieldAnno(ExtendField.Parent)
	protected Integer parent;

	@ExtendFieldAnno(ExtendField.Path)
	protected String path;
	
	@Override
	@Column(name="ext_parent")
	public Integer getParent() {
		return this.parent;
	}

	@Override
	public void setParent(Integer parent) {
		this.parent = parent;
	}

	@Override
	@Column(name="ext_path")
	public String getPath() {
		return path;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

}
