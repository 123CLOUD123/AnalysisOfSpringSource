package com.analysis.processorinterface.impl;

import java.util.List;

import com.analysis.context.BeanFactory;
import com.analysis.processorinterface.BeanFactoryPostProcessor;
import com.analysis.processorinterface.BeanPostProcessor;

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
	
	public static void registerBeanPostProcessors(BeanFactory beanFactory) {
		
		// 获取后置处理器名称
		List<String> processorNames = beanFactory.getBeanForType(BeanPostProcessor.class);
		
		// 注册后置处理器
		for (String name : processorNames) {
			BeanPostProcessor bpp = (BeanPostProcessor) beanFactory.getBean(name);
			beanFactory.addBeanPostProcessor(bpp);
		}
		
	}
	
}
