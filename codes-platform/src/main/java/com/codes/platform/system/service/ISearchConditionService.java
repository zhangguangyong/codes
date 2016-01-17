package com.codes.platform.system.service;

import com.codes.platform.base.service.EntityService;
import com.codes.platform.system.domain.SearchCondition;

/**
 * 搜索条件-业务接口
 * 
 * @author zhangguangyong
 *
 *         2015年11月5日 下午5:13:28
 */
public interface ISearchConditionService extends
		EntityService<SearchCondition, Integer> {
	
	SearchCondition findOneByProperty(String propertyName, Object value);
	
}
