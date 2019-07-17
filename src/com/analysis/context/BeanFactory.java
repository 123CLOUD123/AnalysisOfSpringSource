package com.analysis.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.analysis.bean.BeanDefinition;

/**
 * bean工厂类
 * @author Cloud
 */
public class BeanFactory {

	private Map<String, BeanDefinition> beanMap = new ConcurrentHashMap<>(256);
	
	private List<String> beanNames = new ArrayList<>(256);
	
	public void registerBeanDefinition(String name, BeanDefinition bd) {
		this.beanMap.put(name, bd);
		this.beanNames.add(name);
	}
	
	public List<String> getBeanNames() {
		return this.beanNames;
	}
	
	public BeanDefinition getBeanDefinition(String name) {
		return this.beanMap.get(name);
	}
	
}
