package com.analysis.bean;

public class BeanDefinitionHolder {

	private String name;
	
	private BeanDefinition beanDefinition;
	
	public BeanDefinitionHolder(String name, BeanDefinition bd) {
		this.name = name;
		this.beanDefinition = bd;
	}
	
	public BeanDefinition getBeanDefinition() {
		return this.beanDefinition;
	}
	
	public String getName() {
		return this.name;
	}
}
