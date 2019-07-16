package com.analysis.bean;

import com.analysis.support.Registry;
import com.analysis.util.BeanNameGenerator;

public class BeanReader {

	private Registry registry;
	
	public BeanReader(Registry r) {
		this.registry = r;
	}
	
	public void registerBean(Class<?> annotatedClass) {
		BeanDefinition bd = new BeanDefinition(annotatedClass);
		String beanName = BeanNameGenerator.getName(annotatedClass);
		this.registry.registerBeanDefinition(beanName, bd);
	}
	
}
