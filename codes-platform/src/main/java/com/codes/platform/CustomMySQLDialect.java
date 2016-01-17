package com.codes.platform;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * 自定义的MySQLDialect 为了解决Hibernate自动生成表的时候使用UTF8编码，不然插入中文会有乱码
 * 
 * @author zhangguangyong
 *
 *         2015年11月27日 下午6:10:04
 */
public class CustomMySQLDialect extends MySQL5InnoDBDialect {

	@Override
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}

}
