package com.analysis.processorinterface.impl;

import java.util.List;

import com.analysis.context.BeanFactory;
import com.analysis.processorinterface.BeanFactoryPostProcessor;

public class PostProcessorRegistrationDelegate {

	public static void invokeBeanFactoryPostProcessors(BeanFactory factory, List<BeanFactoryPostProcessor> processors) {
		
		// 调用已存在的BeanFactoryPostProcessor
		for (BeanFactoryPostProcessor bpp : processors) {
			bpp.process(factory);
		}
		
		// 调用内部BeanFactoryPostProcessor
		ConfigurationClassPostProcessor configClass = new ConfigurationClassPostProcessor();
		configClass.process(factory);
		
	}
	
//	public static void 
	
}
