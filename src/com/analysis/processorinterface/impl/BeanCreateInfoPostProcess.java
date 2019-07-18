package com.analysis.processorinterface.impl;

import java.util.logging.Logger;

import com.analysis.processorinterface.BeanPostProcessor;


public class BeanCreateInfoPostProcess implements BeanPostProcessor {

	private final Logger log = Logger.getLogger(getClass().getName());
	

	@Override
	public Object processAfterInit(Object obj, String beanName) {
		log.info(beanName + "已被创建");
		return obj;
	}

}
