package com.analysis.processorinterface;

import com.analysis.bean.BeanDefinition;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

	
	void postProcessPropertyValues(Object obj, String name, BeanDefinition bd);
}
