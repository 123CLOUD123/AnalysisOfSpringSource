package com.analysis.processorinterface;

import com.analysis.context.BeanFactory;

@FunctionalInterface
public interface BeanFactoryPostProcessor {

	void process(BeanFactory factory);
	
}
