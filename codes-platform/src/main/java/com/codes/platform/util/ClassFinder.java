package com.codes.platform.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import com.codes.common.util.$;

/**
 * 类搜索器
 * 
 * @author zhangguangyong
 *
 *         2015年11月7日 上午1:33:50
 */
@SuppressWarnings("all")
public class ClassFinder {

	/** 资源模式解析器 */
	static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	/** 读取元数据工厂 */
	static MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
			resourcePatternResolver);
	/** class文件后缀 */
	static final String CLASS_FILE_SUFFIX = ".class";

	/** 包含的过滤类型 */
	private List<TypeFilter> includeFilters = new ArrayList<TypeFilter>();
	/** 排除的过滤类型 */
	private List<TypeFilter> excludeFilters = new ArrayList<TypeFilter>();
	/** 搜索的报名 */
	private List<String> packageNames = new ArrayList<String>();
	/** 默认优先级为包含的过滤类型 */
	private boolean precedenceIsInclude = true;

	public static ClassFinder getInstance(String packageName) {
		ClassFinder classFinder = new ClassFinder();
		classFinder.getPackageNames().add(packageName);
		return classFinder;
	}

	public ClassFinder addIncludeAnnotation(
			Class<Annotation>... annotationTypes) {
		for (Class<Annotation> cls : annotationTypes) {
			getIncludeFilters().add(new AnnotationTypeFilter(cls));
		}
		return this;
	}

	public ClassFinder addExcludeAnnotation(
			Class<Annotation>... annotationTypes) {
		for (Class<Annotation> cls : annotationTypes) {
			getExcludeFilters().add(new AnnotationTypeFilter(cls));
		}
		return this;
	}

	public ClassFinder addPackageNames(String... packageNames) {
		for (String packageName : packageNames) {
			getPackageNames().add(packageName);
		}
		return this;
	}

	public List<Class<?>> find() {
		List<String> classNames = findClassNames();
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (String className : classNames) {
			try {
				classes.add(Class.forName(className));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return classes;
	}

	public List<String> findClassNames() {
		Set<String> classes = new LinkedHashSet<String>();
		for (String packageName : getPackageNames()) {
			try {
				Resource[] resources = resourcePatternResolver
						.getResources(buildClassSearchPath(packageName));
				MetadataReader metadataReader = null;

				for (Resource resource : resources) {
					if (resource.isReadable()) {
						metadataReader = metadataReaderFactory
								.getMetadataReader(resource);
						if (matches(metadataReader)) {
							classes.add(metadataReader.getClassMetadata()
									.getClassName());
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<String>(classes);
	}

	private String buildClassSearchPath(String packageName) {
		return ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
				+ ClassUtils.convertClassNameToResourcePath(packageName) + "/*"
				+ CLASS_FILE_SUFFIX;
	}

	private boolean matches(MetadataReader metadataReader) throws IOException {
		if ($.isEmpty(getExcludeFilters()) && $.isEmpty(getIncludeFilters())) {
			return true;
		}

		// IncludeFilter 优先
		if (isPrecedenceIsInclude()) {

			for (TypeFilter tf : getIncludeFilters()) {
				if (tf.match(metadataReader, metadataReaderFactory)) {
					return true;
				}
			}

			for (TypeFilter tf : getExcludeFilters()) {
				if (tf.match(metadataReader, metadataReaderFactory)) {
					return false;
				}
			}

		} else {
			// ExcludeFilter 优先
			for (TypeFilter tf : getExcludeFilters()) {
				if (tf.match(metadataReader, metadataReaderFactory)) {
					return false;
				}
			}

			for (TypeFilter tf : getIncludeFilters()) {
				if (tf.match(metadataReader, metadataReaderFactory)) {
					return true;
				}
			}

		}

		// 在Include 和 Exclude 中都没有找到

		// Exclude为空，Include不为空（也就是只需要匹配包含的）
		if ($.isEmpty(getExcludeFilters())) {
			return false;
		}

		// Include为空，Exclude不为空（也就是只需要找匹配排除的）
		if ($.isEmpty(getIncludeFilters())) {
			return true;
		}

		// Include 和 Exclude 都不为空（只要有Include不为空，并且没有匹配上，就返回false）
		return false;
	}

	public List<TypeFilter> getIncludeFilters() {
		return includeFilters;
	}

	public List<TypeFilter> getExcludeFilters() {
		return excludeFilters;
	}

	public List<String> getPackageNames() {
		return packageNames;
	}

	public ClassFinder setIncludeFilters(List<TypeFilter> includeFilters) {
		this.includeFilters = includeFilters;
		return this;
	}

	public ClassFinder setExcludeFilters(List<TypeFilter> excludeFilters) {
		this.excludeFilters = excludeFilters;
		return this;
	}

	public ClassFinder setPackageNames(List<String> packageNames) {
		this.packageNames = packageNames;
		return this;
	}

	public boolean isPrecedenceIsInclude() {
		return precedenceIsInclude;
	}

	public ClassFinder setPrecedenceIsInclude(boolean precedenceIsInclude) {
		this.precedenceIsInclude = precedenceIsInclude;
		return this;
	}

}
