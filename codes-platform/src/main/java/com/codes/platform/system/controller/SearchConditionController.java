package com.codes.platform.system.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codes.common.util.Reflections;
import com.codes.platform.base.web.controller.EntityController;
import com.codes.platform.system.domain.SearchCondition;
import com.codes.platform.system.service.ISearchConditionService;
import com.codes.platform.util.ClassFinder;

/**
 * 搜索条件-控制层
 * 
 * @author zhangguangyong
 *
 *         2015年11月5日 下午5:15:46
 */
@Controller
@RequestMapping("system/SearchCondition")
public class SearchConditionController extends
		EntityController<SearchCondition, Integer> {

	@Autowired
	ISearchConditionService searchConditionService;
	
	@RequestMapping("getEntityNameMap")
	public void doGetEntityNameMap(HttpServletResponse response) {
		List<String> list = ClassFinder.getInstance(
				"com.codes.platform.**.domain").findClassNames();
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		Map<String, String> entityNameMap = null;
		for (String entityName : list) {
			entityNameMap = new LinkedHashMap<String, String>();
			entityNameMap.put("text", entityName);
			entityNameMap.put("value", entityName);
			mapList.add(entityNameMap);
		}
		
		writeJson(response, mapList);
	}
	
	@RequestMapping("getEntityProperty")
	public void doGetEntityProperty(HttpServletResponse response, String entityName) {
		try {
			Class<?> cls = Class.forName(entityName);
			Field[] fields = Reflections.getAllDeclaredFields(cls);
			List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
			Map<String, String> propertyNameMap = null;
			
			for (Field field : fields) {
				propertyNameMap = new LinkedHashMap<String, String>();
				String propertyName = field.getName();
				String propertyType = field.getType().getName();
				propertyNameMap.put("text", propertyType + " " + propertyName);
				propertyNameMap.put("value", propertyName);
				mapList.add(propertyNameMap);
			}
			writeJson(response, mapList);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("saveEntityCondition")
	public void doSaveEntityCondition(HttpServletResponse response, SearchCondition searchCondition) {
		searchConditionService.save(searchCondition);
		writeJson(response, searchCondition);
	}

	@RequestMapping("getEntityCondition")
	public void doGetEntityCondition(HttpServletResponse response, String entityName) {
		List<?> list = baseService.getHqlDao().find("from "+ SearchCondition.class.getName() + " where entityName=?", entityName);
		writeJson(response, list.size() > 0 ? list.get(0) : "" );
	}
	
}
