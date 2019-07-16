package com.dandan.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dandan.bean.BeanDefinition;
import com.dandan.bean.BeanReader;
import com.dandan.support.Registry;

/**
 * 应用程序上下文
 * @author Cloud
 */
public class AppContext implements Registry {

	/*
	 * bean容器
	 */
	private final BeanFactory factory = new BeanFactory();
	
	/*
	 * 
	 */
	private BeanReader reader;
	
	
	/*
	 * bean容器
	 * 用来持有bean
	 */
	private static class BeanFactory {
		private Map<String, BeanDefinition> beanMap = new ConcurrentHashMap<>(256);
		
		public void registerBeanDefinition(String name, BeanDefinition bd) {
			this.beanMap.put(name, bd);
		}
	}
	
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
		
	}


	@Override
	public void register(Class<?>... classes) {
		// TODO Auto-generated method stub
		
	}
	
}
