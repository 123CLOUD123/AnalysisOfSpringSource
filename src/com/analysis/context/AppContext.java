package com.analysis.context;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.analysis.bean.BeanDefinition;
import com.analysis.bean.BeanReader;
import com.analysis.processorinterface.AfterInstantiatedPostProcessor;
import com.analysis.processorinterface.BeanFactoryPostProcessor;
import com.analysis.processorinterface.impl.PostProcessorRegistrationDelegate;
import com.analysis.support.Registry;

/**
 * 应用程序上下文
 * @author Cloud
 */
public class AppContext implements Registry {
	
	private final Logger log = Logger.getLogger(getClass().getName());

	/*
	 * bean容器
	 */
	private final BeanFactory factory = new BeanFactory();
	
	/*
	 * bean读取类
	 */
	private BeanReader reader;
	
	/*
	 * 启动时间
	 */
	private LocalDateTime startUpDate;
	
	/*
	 * 工厂后置处理器
	 */
	private final List<BeanFactoryPostProcessor> factoryPostProcessors = new LinkedList<>();
	
	
	public AppContext(Class<?> configClass) {
		this.reader = new BeanReader(this);
		this.reader.registerBean(configClass);
		this.refresh();
	}

	
	public void refresh() {
		
		prepareRefresh();
		
		// 调用BeanFactory后置处理器
		PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(factory);
		
		// 注册Bean后置处理器
		PostProcessorRegistrationDelegate.registerBeanPostProcessors(factory);
		
		// 完成bean实例化
		finishBeanFactoryInitialization(factory);
		
		// 完成启动
		finishStart(factory);
		
	}
	
	public void finishStart(BeanFactory factory) {
		log.info("----------------");
	}
	
	public void finishBeanFactoryInitialization(BeanFactory factory) {
		
		// 实例化
		Map<String, BeanDefinition> beanMaps = factory.getBeanMap();
		for (Entry<String, BeanDefinition> entry : beanMaps.entrySet()) {
			getBean(entry.getKey());
		}
		
		// 调用AfterInstantiatedPostProcessor
		for (Object obj : factory.getFactoryInstances()) {
			if (obj instanceof AfterInstantiatedPostProcessor) {
				((AfterInstantiatedPostProcessor) obj).afterSingletonsInstantiated();
			}
		}
	}
	
	@Override
	public void registerBeanDefinition(String name, BeanDefinition bd) {
		this.factory.registerBeanDefinition(name, bd);
	}

	
	private void prepareRefresh() {
		this.startUpDate = LocalDateTime.now();
		log.info("系统正在启动...");
	}
	
	public List<BeanFactoryPostProcessor> getFactoryPostProcessors() {
		return this.factoryPostProcessors;
	}
	
	/*
	 * 获取bean工厂
	 */
	public BeanFactory getFactory() {
		return this.factory;
	}
	
	/*
	 * 根据名称获取bean实例
	 */
	public Object getBean(String name) {
		return factory.getBean(name);
	}
	
	
	
}
