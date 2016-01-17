package com.codes.platform.system.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codes.platform.base.web.controller.EntityController;
import com.codes.platform.system.domain.Dictionary;
import com.codes.platform.system.domain.SearchCondition;
import com.codes.platform.system.service.IDictionaryService;
import com.codes.platform.system.service.ISearchConditionService;

/**
 * 搜索条件-控制层
 * 
 * @author zhangguangyong
 *
 *         2015年11月5日 下午5:15:46
 */
@Controller
@RequestMapping("system/Dictionary")
public class DictionaryController extends
		EntityController<SearchCondition, Integer> {

	@Autowired
	IDictionaryService dictionaryService;

	@Autowired
	ISearchConditionService conditionService;
	
	@RequestMapping("getSearchCondition")
	public void doGetSearchCondition(HttpServletResponse response) {
		SearchCondition condition = conditionService.findOneByProperty("entityName", Dictionary.class.getName());
		writeJson(response, condition);
	}
}
