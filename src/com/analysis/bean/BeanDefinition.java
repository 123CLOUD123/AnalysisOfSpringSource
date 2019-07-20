package com.analysis.bean;

import com.analysis.metadata.AnnotationMetadata;
import com.analysis.metadata.InjectMetadata;

/**
 * 
 * @author Cloud
 *
 */
public class BeanDefinition {

	private final AnnotationMetadata am;
	
	private InjectMetadata im;
	
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
	
	public InjectMetadata getInjectMetadata() {
		return im;
	}
	
	public void setInjectMetadata(InjectMetadata im) {
		this.im = im;
	}
}
