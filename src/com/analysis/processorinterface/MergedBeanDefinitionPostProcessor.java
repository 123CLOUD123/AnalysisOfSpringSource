package com.analysis.processorinterface;

import com.analysis.bean.BeanDefinition;

public interface MergedBeanDefinitionPostProcessor extends BeanPostProcessor {

	void postProcessMergedBeanDefinition(BeanDefinition beanDefinition, String beanName);
	
}
