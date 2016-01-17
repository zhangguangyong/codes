package com.codes.platform.system.service.impl;

import org.springframework.stereotype.Service;

import com.codes.platform.base.service.impl.EntityServiceImpl;
import com.codes.platform.system.domain.Dictionary;
import com.codes.platform.system.service.IDictionaryService;

/**
 * 字典-业务接口实现
 * 
 * @author zhangguangyong
 *
 *         2015年11月9日 下午1:44:32
 */
@Service
public class DictionaryServiceImpl extends
		EntityServiceImpl<Dictionary, Integer> implements IDictionaryService {
}
