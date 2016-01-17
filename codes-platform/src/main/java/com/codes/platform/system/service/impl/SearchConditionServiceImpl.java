package com.codes.platform.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codes.platform.base.service.impl.EntityServiceImpl;
import com.codes.platform.system.domain.SearchCondition;
import com.codes.platform.system.service.ISearchConditionService;

/**
 * 搜索条件-业务接口实现
 * 
 * @author zhangguangyong
 *
 *         2015年11月5日 下午5:14:37
 */
@Service
public class SearchConditionServiceImpl extends
		EntityServiceImpl<SearchCondition, Integer> implements
		ISearchConditionService {
	
	@Override
	public void save(SearchCondition entity) {
		List<?> list = getHqlDao().find("from "+ SearchCondition.class.getName() + " where entityName=?", entity.getEntityName());
		if( list.size() > 0 ){
			SearchCondition dbObj = (SearchCondition)list.get(0);
			entity.setId( dbObj.getId() );
		}
		super.save(entity);
	}

	@Override
	public SearchCondition findOneByProperty(String propertyName, Object value) {
		List<?> list = getHqlDao().find("from " + SearchCondition.class.getName() + " where " + propertyName + "=?", value);
		if( list.size() > 0 ){
			return (SearchCondition) list.get(0);
		}
		return null;
	}
	
}
