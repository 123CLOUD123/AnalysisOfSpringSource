package com.analysis.bean;

import com.analysis.metadata.AnnotationMetadata;

/**
 * 
 * @author Cloud
 *
 */
public class BeanDefinition {

	private final AnnotationMetadata am;
	
	private volatile Object beanClass;
	
	public BeanDefinition(Class<?> clazz) {
		this.beanClass = clazz;
		this.am = new AnnotationMetadata(clazz);
	}
	
	public AnnotationMetadata getAnnotationMetadata() {
		return this.am;
	}
	
	public Class<?> getBeanClass() {
		return (Class<?>) this.beanClass;
	}
}
