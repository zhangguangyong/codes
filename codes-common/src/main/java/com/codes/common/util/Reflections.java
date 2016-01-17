package com.codes.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 * 
 * @author zhangguangyong
 *
 *         2015年11月27日 下午6:28:47
 */
@SuppressWarnings("rawtypes")
public abstract class Reflections {

	/*---------------------------字段--------------------------------*/

	/**
	 * 根据字段名称获取字段,这里只能获取public权限的字段
	 *
	 * @param cls
	 * @param fieldName
	 * @return
	 */
	public static Field getField(final Class cls, final String fieldName) {
		return getField(cls, fieldName, false);
	}

	/**
	 * 根据字段名称获取字段
	 *
	 * @param cls
	 * @param fieldName
	 * @return
	 */
	public static Field getDeclaredField(final Class cls, final String fieldName) {
		return getField(cls, fieldName, true);
	}

	/**
	 * 根据字段名称和指定的权限获取字段
	 *
	 * @param cls
	 * @param fieldName
	 * @param declared
	 * @return
	 */
	private static Field getField(final Class cls, final String fieldName, boolean declared) {
		$.checkNotEmpty(fieldName);
		List<Field> fields = getFieldsList(cls, declared);
		for (int i = 0; i < fields.size(); i++) {
			if (fieldName.equals(fields.get(i).getName())) {
				return fields.get(i);
			}
		}
		return null;
	}

	/**
	 * 获取有字段，这里获取的是public权限的字段
	 *
	 * @param cls
	 * @return
	 */
	public static Field[] getAllFields(final Class cls) {
		return getFieldsList(cls, false).toArray(new Field[0]);
	}

	/**
	 * 获取所有字段
	 *
	 * @param cls
	 * @return
	 */
	public static Field[] getAllDeclaredFields(final Class cls) {
		return getFieldsList(cls, true).toArray(new Field[0]);
	}

	/**
	 * 根据指定的权限获取所有字段
	 *
	 * @param cls
	 * @param declared
	 * @return
	 */
	private static List<Field> getFieldsList(final Class cls, final boolean declared) {
		$.checkNotNull(cls);

		final List<Field> allFields = new ArrayList<Field>();
		Class currentClass = cls;
		while (currentClass != null) {
			final Field[] declaredFields = declared ? currentClass.getDeclaredFields() : currentClass.getFields();
			for (Field field : declaredFields) {
				allFields.add(field);
			}
			currentClass = currentClass.getSuperclass();
		}
		return allFields;
	}

	/*---------------------------方法--------------------------------*/

	/**
	 * 根据方法名称获取方法，这里只能获取public权限的方法
	 *
	 * @param cls
	 * @param methodName
	 * @return
	 */
	public static Method getMethod(final Class cls, final String methodName) {
		return getMethod(cls, methodName, false);
	}

	/**
	 * 根据方法名称获取方法
	 *
	 * @param cls
	 * @param methodName
	 * @return
	 */
	public static Method getDeclaredMethod(final Class cls, final String methodName) {
		return getMethod(cls, methodName, true);
	}

	/**
	 * 获取所有方法，这里只能获取public权限的方法
	 *
	 * @param cls
	 * @return
	 */
	public static Method[] getAllMethods(final Class cls) {
		return getMethodsList(cls, false).toArray(new Method[0]);
	}

	/**
	 * 获取所有方法
	 *
	 * @param cls
	 * @return
	 */
	public static Method[] getAllDeclaredMethods(final Class cls) {
		return getMethodsList(cls, true).toArray(new Method[0]);
	}

	/**
	 * 获取所有指定权限的方法
	 *
	 * @param cls
	 * @param declared
	 * @return
	 */
	private static List<Method> getMethodsList(final Class cls, final boolean declared) {
		$.checkNotNull(cls);

		final List<Method> allMethods = new ArrayList<Method>();
		Class currentClass = cls;
		while (currentClass != null) {
			final Method[] methods = declared ? currentClass.getDeclaredMethods() : currentClass.getMethods();
			for (Method method : methods) {
				allMethods.add(method);
			}
			currentClass = currentClass.getSuperclass();
		}
		return allMethods;
	}

	/**
	 * 根据指定的方法名称和权限获取方法
	 *
	 * @param cls
	 * @param methodName
	 * @param declared
	 * @return
	 */
	private static Method getMethod(final Class cls, final String methodName, boolean declared) {
		$.checkNotEmpty(methodName);
		List<Method> methods = getMethodsList(cls, declared);
		for (int i = 0; i < methods.size(); i++) {
			if (methodName.equals(methods.get(i).getName())) {
				return methods.get(i);
			}
		}
		return null;
	}

	/*---------------------------泛型--------------------------------*/

	/**
	 * 获取第一个位置的泛型
	 *
	 * @param cls
	 * @return
	 */
	public static Class getGeneric(final Class cls) {
		return getGeneric(cls, 0);
	}

	/**
	 * 获取指定位置的泛型
	 *
	 * @param cls
	 * @param index
	 * @return
	 */
	public static Class getGeneric(final Class cls, final int index) {
		Type[] generics = getGenerics(cls);
		if ($.notEmpty(generics)) {
			return (Class) generics[index];
		}
		return null;
	}

	/**
	 * 获取所有泛型
	 *
	 * @param cls
	 * @return
	 */
	public static Type[] getGenerics(Class cls) {
		$.checkNotNull(cls);

		Type genType = cls.getGenericSuperclass();
		if (genType instanceof ParameterizedType) {
			return ((ParameterizedType) genType).getActualTypeArguments();
		}

		return null;
	}
}
