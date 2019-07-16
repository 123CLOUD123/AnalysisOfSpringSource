package com.dandan.support;

import com.dandan.bean.BeanDefinition;

/**
 * 注册bean
 * @author Cloud
 *
 */
public interface Registry {

	void register(Class<?>... classes);

	void registerBeanDefinition(String name, BeanDefinition bd);
	
}
