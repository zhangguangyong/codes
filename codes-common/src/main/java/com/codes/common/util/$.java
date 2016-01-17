package com.codes.common.util;

import java.lang.reflect.Array;
import java.util.Map;

/**
 * 常用工具方法集合
 * 
 * @author zhangguangyong
 *
 *         2015年11月27日 下午6:13:48
 */
public abstract class $ {
	/*------------------------------ 空值判断 -------------------------------*/
	/**
	 * 为Null判断
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNull(Object value) {
		return null == value;
	}

	/**
	 * 为空判断
	 * 
	 * @param value
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object value) {
		if (isNull(value)) {
			return true;
		}

		Class<? extends Object> valueClass = value.getClass();
		// 数组
		if (valueClass.isArray()) {
			return Array.getLength(value) <= 0;
		}

		// 线性数据
		if (Iterable.class.isAssignableFrom(valueClass)) {
			return !((Iterable) value).iterator().hasNext();
		}

		// 键值对
		if (Map.class.isAssignableFrom(valueClass)) {
			return ((Map) value).isEmpty();
		}

		// 字符序列
		if (CharSequence.class.isAssignableFrom(valueClass)) {
			return ((CharSequence) value).toString().trim().length() <= 0;
		}

		return false;
	}

	/**
	 * 非Null判断
	 * 
	 * @param value
	 * @return
	 */
	public static boolean notNull(Object value) {
		return !isNull(value);
	}

	/**
	 * 非空判断
	 * 
	 * @param value
	 * @return
	 */
	public static boolean notEmpty(Object value) {
		return !isEmpty(value);
	}

	/*------------------------------ 空值检查 -------------------------------*/
	public static <T> T checkNotNull(T reference) {
		if (isNull(reference)) {
			throw new NullPointerException();
		}
		return reference;
	}

	public static <T> T checkNotNull(T reference, Object errorMessage) {
		if (isNull(reference)) {
			throw new NullPointerException(String.valueOf(errorMessage));
		}
		return reference;
	}

	public static <T> T checkNotNull(T reference, String errorMessageTemplate, Object... errorMessageArgs) {
		if (isNull(reference)) {
			// If either of these parameters is null, the right thing happens
			// anyway
			throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
		}
		return reference;
	}

	public static <T> T checkNotEmpty(T reference) {
		if (isEmpty(reference)) {
			throw new IllegalArgumentException();
		}
		return reference;
	}

	public static <T> T checkNotEmpty(T reference, Object errorMessage) {
		if (isEmpty(reference)) {
			throw new IllegalArgumentException(String.valueOf(errorMessage));
		}
		return reference;
	}

	public static <T> T checkNotEmpty(T reference, String errorMessageTemplate, Object... errorMessageArgs) {
		if (isEmpty(reference)) {
			throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
		}
		return reference;
	}

	static String format(String template, Object... args) {
		template = String.valueOf(template); // null -> "null"

		// start substituting the arguments into the '%s' placeholders
		StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
		int templateStart = 0;
		int i = 0;
		while (i < args.length) {
			int placeholderStart = template.indexOf("%s", templateStart);
			if (placeholderStart == -1) {
				break;
			}
			builder.append(template.substring(templateStart, placeholderStart));
			builder.append(args[i++]);
			templateStart = placeholderStart + 2;
		}
		builder.append(template.substring(templateStart));

		// if we run out of placeholders, append the extra args in square braces
		if (i < args.length) {
			builder.append(" [");
			builder.append(args[i++]);
			while (i < args.length) {
				builder.append(", ");
				builder.append(args[i++]);
			}
			builder.append(']');
		}

		return builder.toString();
	}

}
