package com.analysis.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.analysis.bean.BeanDefinition;
import com.analysis.exception.BeanException;
import com.analysis.processorinterface.BeanFactoryPostProcessor;
import com.analysis.processorinterface.BeanPostProcessor;
import com.analysis.processorinterface.InitializingBean;
import com.analysis.processorinterface.InstantiationAwareBeanPostProcessor;
import com.analysis.processorinterface.MergedBeanDefinitionPostProcessor;
import com.analysis.support.BeanFactoryAware;

/**
 * bean工厂类
 * @author Cloud
 */
public class BeanFactory {

	/* 注册的bean */
	private Map<String, BeanDefinition> beanMap = new ConcurrentHashMap<>(256);
	
	/* 已注册的bean名称 */
	private List<String> beanNames = new ArrayList<>(256);
	
	/* 已实例化的bean */
	private Map<String, Object> objects = new ConcurrentHashMap<>(256);
	
	/* Bean后置处理器实例 */
	private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
	
	/*
	 * 获取所有实例
	 */
	public List<Object> getFactoryInstances() {
		return new ArrayList<>(objects.values());
	}
	
	/*
	 * 获取Bean后置处理器
	 */
	public List<BeanPostProcessor> getBeanPostProcessors() {
		return this.beanPostProcessors;
	}
	
	public void addBeanPostProcessor(BeanPostProcessor bpp) {
		this.beanPostProcessors.add(bpp);
	}
	
	/*
	 * 获取已注册的bean
	 */
	public Map<String, BeanDefinition> getBeanMap() {
		return beanMap;
	}
	
	
	
	/*
	 * 注册bean
	 */
	public void registerBeanDefinition(String name, BeanDefinition bd) {
		this.beanMap.put(name, bd);
		this.beanNames.add(name);
	}
	
	/*
	 * 获取已注册的bean名称
	 */
	public List<String> getBeanNames() {
		return this.beanNames;
	}
	
	/*
	 * 获取Bean
	 */
	public BeanDefinition getBeanDefinition(String name) {
		return this.beanMap.get(name);
	}
	
	/*
	 * 根据类型获取Bean
	 */
	public List<String> getBeanForType(Class<?> clazz) {
		List<String> beanNames = new ArrayList<>();
		for (Entry<String, BeanDefinition> entry : beanMap.entrySet()) {
			Class<?> a = entry.getValue().getBeanClass();
			if (clazz.isAssignableFrom(a)) {
				beanNames.add(entry.getKey());
			}
		}
		return beanNames;
	}
	
	/*
	 * 根据名称获取bean实例
	 */
	public Object getBean(String name) {
		Object obj = objects.get(name);
		if (obj == null) {
			obj = createBean(name);
			this.objects.put(name, obj);
		}
		return obj;
	}
	
	/*
	 * 创建bean实例
	 */
	public Object createBean(String name) {
		
		//先检查该bean名称是否已注册,若未注册，说明程序有问题，需解决
		BeanDefinition bd = beanMap.get(name);
		if (bd == null) {
			throw new BeanException("bean名称:" + name + "未注册。");
		}
		
		return doCreateBean(name, bd);
	}
	
	/*
	 * 根据类型查询已注册的bean
	 */
	public String resolveBeanNameByType(Class<?> clazz) {
		
		String name = null;
		for (Entry<String, BeanDefinition> entry : beanMap.entrySet()) {
			BeanDefinition bd = entry.getValue();
			Class<?> internalClass = bd.getBeanClass();
			if (internalClass == clazz) {
				name = entry.getKey();
				break;
			}
		}
		
		return name;
	}
	
	public Object doCreateBean(String name, BeanDefinition bd) {
		Object obj = null;
		Class<?> clazz = bd.getBeanClass();
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			throw new BeanException("bean初始化失败");
		}
		
		// 调用BeanFactoryAware
		applyBeanFactoryAware();
		
		// 调用MergerBeanDefinitionPostProcessor
		applyMergedBeanDefinitionPostProcessor(bd, name);
		
		// 对bean进行依赖注入
		populate(name, bd, obj);
		
		// 初始化bean
		obj = initializeBean(name, bd, obj);
		
		return obj;
		
	}
	
	public void applyBeanFactoryAware() {
		for (BeanPostProcessor bpp : getBeanPostProcessors()) {
			if (bpp instanceof BeanFactoryAware) {
				((BeanFactoryAware) bpp).setFactory(this);
			}
		}
	}
	
	public void populate(String name, BeanDefinition bd, Object obj) {
		for (BeanPostProcessor bpp : getBeanPostProcessors()) {
			if (bpp instanceof InstantiationAwareBeanPostProcessor) {
				((InstantiationAwareBeanPostProcessor) bpp).postProcessPropertyValues(obj, name, bd);
			}
		}
	}
	
	
	public Object initializeBean(String name, BeanDefinition bd, Object obj) {
		
		obj = applyBeanPostProcessorsBeforeInitialization(name, obj);
		invokeInitMethod(name, bd, obj);
		obj = applyBeanPostProcessorsAfterInitialization(name, obj);
		
		return obj;
	}
	
	public void invokeInitMethod(String name, BeanDefinition bd, Object obj) {
		if (obj instanceof InitializingBean) {
			((InitializingBean) obj).afterPropertiesSet();
		}
	}
	
	public Object applyBeanPostProcessorsBeforeInitialization(String name, Object obj) {
		Object instance = obj;
		for (BeanPostProcessor bpp : getBeanPostProcessors()) {
			instance = bpp.processBeforeInit(instance, name);
		}
		return instance;
	}
	
	public Object applyBeanPostProcessorsAfterInitialization(String name, Object obj) {
		Object instance = obj;
		for (BeanPostProcessor bpp : getBeanPostProcessors()) {
			instance = bpp.processAfterInit(instance, name);
		}
		return instance;
	}
	
	public void applyMergedBeanDefinitionPostProcessor(BeanDefinition bd, String name) {
		for (BeanPostProcessor bpp : getBeanPostProcessors()) {
			if (bpp instanceof MergedBeanDefinitionPostProcessor) {
				((MergedBeanDefinitionPostProcessor) bpp).postProcessMergedBeanDefinition(bd, name);
			}
		}
	}
	
	
	
}
