package com.analysis.processorinterface.impl;

import java.util.ArrayList;
import java.util.List;

import com.analysis.bean.BeanDefinition;
import com.analysis.context.BeanFactory;
import com.analysis.processorinterface.BeanFactoryPostProcessor;
import com.analysis.processorinterface.BeanPostProcessor;
import com.analysis.support.Ordered;
import com.analysis.support.PriorityOrdered;

public class PostProcessorRegistrationDelegate {

	public static void invokeBeanFactoryPostProcessors(BeanFactory factory) {

		// 获取bean工厂后置处理器
		List<String> processors = factory.getBeanForType(BeanFactoryPostProcessor.class);

		// 将后置处理器进行分类
		List<BeanFactoryPostProcessor> priorityProcessors = new ArrayList<>();
		List<BeanFactoryPostProcessor> secondProcessors = new ArrayList<>();
		List<BeanFactoryPostProcessor> otherProcessors = new ArrayList<>();
		for (String beanName : processors) {

			BeanDefinition bd = factory.getBeanDefinition(beanName);
			Class<?> clazz = bd.getBeanClass();
			if (PriorityOrdered.class.isAssignableFrom(clazz)) {
				priorityProcessors.add((BeanFactoryPostProcessor) factory.getBean(beanName));
			} else if (Ordered.class.isAssignableFrom(clazz)) {
				secondProcessors.add((BeanFactoryPostProcessor) factory.getBean(beanName));
			} else {
				otherProcessors.add((BeanFactoryPostProcessor) factory.getBean(beanName));
			}
		}

		// 首先执行实现了PriorityOrdered的后置处理
		for (BeanFactoryPostProcessor bpp : priorityProcessors) {
			bpp.process(factory);
		}

		// 然后执行实现了Ordered接口的后置处理
		for (BeanFactoryPostProcessor bpp : secondProcessors) {
			bpp.process(factory);
		}

		// 最后执行实现了Ordered接口的后置处理
		for (BeanFactoryPostProcessor bpp : otherProcessors) {
			bpp.process(factory);
		}

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
