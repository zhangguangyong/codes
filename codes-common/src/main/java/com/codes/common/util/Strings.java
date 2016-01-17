package com.codes.common.util;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

/**
 * 字符串工具类
 * 
 * @author zhangguangyong
 *
 *         2015年11月27日 下午6:29:12
 */
public abstract class Strings {
	/**
	 * 重复拼接
	 *
	 * @param string
	 *            重复拼接的字符串
	 * @param count
	 *            拼接次数
	 * @return
	 */
	public static String repeat(String string, int count) {
		return repeat(string, "", count);
	}

	/**
	 * 重复拼接字符串
	 *
	 * @param string
	 *            重复拼接的字符串
	 * @param separator
	 *            拼接的分隔符
	 * @param count
	 *            拼接次数
	 * @return
	 */
	public static String repeat(String string, String separator, int count) {
		// 参数验证
		$.checkNotNull(string);
		Preconditions.checkArgument(count >= 0, "invalid count: %s", count);
		String sep = null == separator ? "" : separator;

		StringBuffer sb = new StringBuffer();
		int forCount = count - 1;
		for (int i = 0; i < forCount; i++) {
			sb.append(string).append(sep);
		}
		sb.append(string);

		return sb.toString();
	}

	/**
	 * 把数组元素通过指定的分隔符连接成为字符串
	 *
	 * @param array
	 *            数组
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String join(final Object array, final String separator) {
		$.checkNotEmpty(array);
		Class<?> arrayType = array.getClass();
		Preconditions.checkArgument(arrayType.isArray(), "parameter array must be Array: %s", array);

		StringBuffer sb = new StringBuffer();
		sb.append(Array.get(array, 0));

		int length = Array.getLength(array);
		for (int i = 1; i < length; i++) {
			sb.append(separator).append(Array.get(array, i));
		}

		return sb.toString();
	}

	/**
	 * 把对象值拼接起来组成字符串
	 *
	 * @param separator
	 *            分隔符
	 * @param values
	 *            被拼接的对象
	 * @return
	 */
	public static String join(String separator, Object... values) {
		return join(Arrays.asList(values), separator);
	}

	/**
	 * 把可迭代对象里面的元素拼接起来组成字符创
	 *
	 * @param iterable
	 *            可迭代对象
	 * @param separator
	 *            拼接字符串
	 * @return
	 */
	public static String join(Iterable<?> iterable, String separator) {
		return Joiner.on(separator).join(iterable);
	}

}
