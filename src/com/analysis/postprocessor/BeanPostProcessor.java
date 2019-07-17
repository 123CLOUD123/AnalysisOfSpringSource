package com.analysis.postprocessor;

/**
 * Bean后置处理器
 * 实现此接口可以在Bean初始化前后执行操作
 * @author Cloud
 *
 */
public interface BeanPostProcessor {

	/*
	 * Bean初始化之前执行
	 */
	default Object processBeforeInit(Object obj, String beanName) {
		return obj;
	}
	
	/*
	 * Bean初始化后执行
	 */
	default Object processAfterInit(Object obj, String beanName) {
		return obj;
	}
	
}
