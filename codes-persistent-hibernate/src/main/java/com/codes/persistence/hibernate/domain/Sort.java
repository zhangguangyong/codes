package com.codes.persistence.hibernate.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;

/**
 * 排序封装
 * @author zhangguangyong
 *
 * 2015年10月27日 下午7:09:39
 */
public class Sort {
	private List<Order> orders = new ArrayList<Order>();
	
	public static Sort asc(String...properties){
		Sort sort = new Sort();
		return sort.addAsc(properties);
	}
	
	public static Sort desc(String...properties){
		Sort sort = new Sort();
		return sort.addDesc(properties);
	}
	
	public Sort addAsc(String...properties){
		return addOrder(true, properties);
	}
	
	public Sort addDesc(String...properties){
		return addOrder(false, properties);
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	private Sort addOrder(boolean isAsc, String...properties){
		for (String p : properties) {
			getOrders().add( isAsc ? Order.asc(p) : Order.desc(p) );
		}
		return this;
	}
	
}
