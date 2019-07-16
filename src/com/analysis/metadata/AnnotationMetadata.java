package com.analysis.metadata;

import java.lang.annotation.Annotation;

/**
 * 注解信息
 * @author Cloud
 *
 */
public class AnnotationMetadata {

	private final Annotation[] annotations;
	
	private final Class<?> clazz;
	
	public AnnotationMetadata(Class<?> clazz) {
		this.annotations = clazz.getAnnotations();
		this.clazz = clazz;
	}
	
}
