package com.codes.persistence.hibernate.listener;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.criterion.Projections;
import org.hibernate.event.internal.DefaultSaveEventListener;
import org.hibernate.event.spi.EventSource;
import org.hibernate.event.spi.SaveOrUpdateEvent;

import static com.codes.common.util.$.*;

import com.codes.persistence.hibernate.domain.Entity;
import com.codes.persistence.hibernate.domain.support.Auditable;
import com.codes.persistence.hibernate.domain.support.Dateable;
import com.codes.persistence.hibernate.domain.support.Movable;
import com.codes.persistence.hibernate.domain.support.Treeable;

@SuppressWarnings("all")
public class CustomSaveListener extends DefaultSaveEventListener {

	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		System.out.println("-------------CustomSaveListener-------------");

		Object entity = event.getEntity();
		Class<? extends Object> clazz = entity.getClass();
		EventSource session = event.getSession();
		
		// 可记录时间的
		if (Dateable.class.isAssignableFrom(clazz)) {
			Date now = new Date();
			((Dateable) entity).setCreateDate(now);
			((Dateable) entity).setLastModifiedDate(now);
		}

		// 可审核的
		if (Auditable.class.isAssignableFrom(clazz)) {
			// TODO 获取系统当前登录用户
		}

		// 可移动的
		if (Movable.class.isAssignableFrom(clazz)) {
			// 获取最后一条件
			Integer maxIndex = 1;
			
			// Object maxVal = session.createCriteria(clazz).setProjection(Projections.max("index")).uniqueResult();
			Object maxVal = session.createQuery("select max(index) from " + clazz.getName()).uniqueResult();
			
			if( notNull( maxVal ) ){
				maxIndex = Integer.valueOf(maxVal.toString()) + 1;
			}
			((Movable) entity).setIndex(maxIndex);
		}
		
		// 可树形结构化的
		if (Treeable.class.isAssignableFrom(clazz)) {
			// 设置节点自身的位置路径
			Treeable self = ((Treeable) entity);
			// 获取上级节点
			Serializable parentId = self.getParent();
			if( isNull(parentId) ){
				self.setPath("/"); 	//没有上级节点的节点
			}else{
				Treeable parent = (Treeable) session.get(clazz, parentId);
				self.setPath( parent.getPath() + "/" + ( (Entity) self ).getId().toString() );
			}
		}
		event.setEntity(entity);

		super.onSaveOrUpdate(event);
	}

}
