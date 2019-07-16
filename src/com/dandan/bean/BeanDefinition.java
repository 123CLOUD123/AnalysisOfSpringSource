package com.dandan.bean;

import com.dandan.metadata.AnnotationMetadata;

/**
 * 
 * @author Cloud
 *
 */
public class BeanDefinition {

	private final AnnotationMetadata am;
	
	private volatile Object beanClass;
	
	BeanDefinition(Class<?> clazz) {
		this.beanClass = clazz;
		this.am = new AnnotationMetadata(clazz);
	}
}
