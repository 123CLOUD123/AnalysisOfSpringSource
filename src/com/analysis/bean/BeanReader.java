package com.analysis.bean;

import com.analysis.processorinterface.impl.AutowiredAnnotationBeanPostProcessor;
import com.analysis.processorinterface.impl.BeanCreateInfoPostProcess;
import com.analysis.processorinterface.impl.ConfigurationClassPostProcessor;
import com.analysis.support.ConfigurationClassParser;
import com.analysis.support.Registry;
import com.analysis.util.BeanNameGenerator;

public class BeanReader {

	private Registry registry;
	
	public BeanReader(Registry r) {
		this.registry = r;
		registerInternalPostProcessor();
	}
	
	/*
	 * 注册内部回调
	 */
	public void registerInternalPostProcessor() {
		registerBean(ConfigurationClassPostProcessor.class);
		registerBean(BeanCreateInfoPostProcess.class);
		registerBean(AutowiredAnnotationBeanPostProcessor.class);
	}
	
	/*
	 * 注册bean
	 */
	public void registerBean(Class<?> annotatedClass) {
		BeanDefinition bd = new BeanDefinition(annotatedClass);
		String beanName = BeanNameGenerator.getName(annotatedClass);
		this.registry.registerBeanDefinition(beanName, bd);
	}
	
}
