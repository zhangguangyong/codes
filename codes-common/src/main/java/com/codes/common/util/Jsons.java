package com.codes.common.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * JSON工具类 实现借助于alibaba的fast-json
 * 
 * @author zhangguangyong
 *
 *         2015年11月27日 下午6:22:02
 */
public abstract class Jsons {

	static SerializerFeature[] features = new SerializerFeature[] { SerializerFeature.WriteDateUseDateFormat };

	/**
	 * 把一个对象转换成json字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String format(Object value) {
		return JSON.toJSONString(value, features);
	}

	/**
	 * 把字符串解析成Java对象
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> T parse(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}
	
	/**
	 * 把字符串解析成Java集合
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}
}
