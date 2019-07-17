package com.analysis.context;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.analysis.bean.BeanDefinition;
import com.analysis.bean.BeanReader;
import com.analysis.postprocessor.BeanFactoryPostProcessor;
import com.analysis.postprocessor.PostProcessorRegistrationDelegate;
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


	@Override
	public void registerBeanDefinition(String name, BeanDefinition bd) {
		this.factory.registerBeanDefinition(name, bd);
		
	}
	
	public void refresh() {
		
		prepareRefresh();
		
		// 调用工厂后置处理器
		PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(factory, getFactoryPostProcessors());
		
		
		
	}


	@Override
	public void register(Class<?>... classes) {
		// TODO Auto-generated method stub
		
	}
	
	private void prepareRefresh() {
		this.startUpDate = LocalDateTime.now();
		log.info("系统正在启动...");
	}
	
	public List<BeanFactoryPostProcessor> getFactoryPostProcessors() {
		return this.factoryPostProcessors;
	}
	
	
	
}
