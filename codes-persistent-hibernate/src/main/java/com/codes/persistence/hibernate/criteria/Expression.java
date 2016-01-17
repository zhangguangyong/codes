package com.codes.persistence.hibernate.criteria;
/**
 * 查询表达式
 * @author zhangguangyong
 *
 * 2015年10月7日 下午9:04:08
 */
public enum Expression {
	// 等于、不等于、大于、大于等于、小于、小于等于-------------------------------------
	eq,
	eqProperty,
	eqOrIsNull,
	
	ne,
	neOrIsNotNull,
	neProperty,
	
	gt,
	gtProperty,
	ge,
	geProperty,
	
	lt,
	ltProperty,
	le,
	leProperty,
	
	// 属性非空判断-------------------------------------
	isEmpty,
	isNotEmpty,
	isNull,
	isNotNull,
	isNullOrIsEmpty,
	
	// id等于-------------------------------------
	idEq,
	
	// 特殊处理的 in,not in, between, not between-------------------------------------
	in,
	nin, 
	between,
	nbetween,
	
	// like, not like, ilike, not ilike-------------------------------------
	like, alike, slike, elike, exlike,
	nlike,nalike, nslike, nelike, nexlike, 
	
	ilike, ailike, silike, eilike, exilike,
	nilike,nailike, nsilike, neilike, nexilike
	
}
