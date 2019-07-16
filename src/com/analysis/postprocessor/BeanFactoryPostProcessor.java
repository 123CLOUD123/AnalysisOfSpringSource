package com.analysis.postprocessor;

import com.analysis.context.BeanFactory;

@FunctionalInterface
public interface BeanFactoryPostProcessor {

	void run(BeanFactory factory);
	
}
