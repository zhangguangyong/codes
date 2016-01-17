package com.codes.platform.base.web.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codes.common.util.$;
import com.codes.common.util.Reflections;
import com.codes.persistence.hibernate.domain.Entity;
import com.codes.platform.base.service.EntityService;

/**
 * 实体控制器
 * 
 * @author zhangguangyong
 *
 *         2015年11月5日 下午4:17:53
 */
public abstract class EntityController<T extends Entity<ID>, ID extends Serializable>
		extends BaseController {

	private EntityService<T, ID> entityService;
	private Class<T> entityClass;
	private String viewPrefix;

	@SuppressWarnings("unchecked")
	public EntityController() {
		this.entityClass = Reflections.getGeneric(getClass());
		setViewPrefix(defaultViewPrefix());
	}

	public EntityService<T, ID> getEntityService() {
		return entityService;
	}

	@Autowired
	public void setEntityService(EntityService<T, ID> entityService) {
		this.entityService = entityService;
	}

	public String getViewPrefix() {
		return viewPrefix;
	}

	public void setViewPrefix(String viewPrefix) {
		if (viewPrefix.startsWith("/")) {
			viewPrefix = viewPrefix.substring(1);
		}
		this.viewPrefix = viewPrefix;
	}

	/**
	 * 跳转到列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toList")
	public String toList() {
		return viewName("list");
	}

	@RequestMapping("/doList")
	public void doList(HttpServletResponse response) {
		writeJson(response, entityService.findAll());
	}

	@RequestMapping("/doPage")
	public void doPage() {
	}

	@RequestMapping("/doSearch")
	public void doSearch() {
	}
	

	/**
	 * 获取跳转的视图名称
	 * @param viewSuffix
	 * @return
	 */
	protected String viewName(String viewSuffix) {
		if (!viewSuffix.startsWith("/")) {
			viewSuffix = "/" + viewSuffix;
		}
		return getViewPrefix() + viewSuffix;
	}

	/**
	 * 获取默认的视图前缀
	 * 
	 * @return
	 */
	protected String defaultViewPrefix() {
		String currentViewPrefix = "";
		RequestMapping rm = getClass().getAnnotation(RequestMapping.class);

		if (rm != null && rm.value().length > 0) {
			currentViewPrefix = rm.value()[0];
		}

		if ($.isEmpty(currentViewPrefix)) {
			currentViewPrefix = this.entityClass.getSimpleName();
		}

		return currentViewPrefix;
	}
}
