package com.analysis.processorinterface.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.analysis.annotation.AutoInject;
import com.analysis.bean.BeanDefinition;
import com.analysis.context.BeanFactory;
import com.analysis.exception.BeanException;
import com.analysis.metadata.InjectMetadata;
import com.analysis.processorinterface.InstantiationAwareBeanPostProcessor;
import com.analysis.processorinterface.MergedBeanDefinitionPostProcessor;
import com.analysis.support.BeanFactoryAware;
import com.analysis.util.Assert;

public class AutowiredAnnotationBeanPostProcessor
		implements MergedBeanDefinitionPostProcessor, InstantiationAwareBeanPostProcessor, BeanFactoryAware {
	
	private BeanFactory factory;

	@Override
	public void postProcessPropertyValues(Object obj, String name, BeanDefinition bd) {
		InjectMetadata im = bd.getInjectMetadata();
		if (im == null) {
			return;
		}
		
		List<Field> fs = im.getFields();
		if (fs == null || fs.size() == 0) {
			return;
		}
		
		for (Field f : fs) {
			Class<?> clazz = (Class<?>) f.getGenericType();
			String beanName = factory.resolveBeanNameByType(clazz);
			if (beanName == null) {
				throw new BeanException("类" + clazz + "未注册");
			}
			Object injectObj = factory.getBean(beanName);
			try {
				f.setAccessible(true);
				f.set(obj, injectObj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void postProcessMergedBeanDefinition(BeanDefinition beanDefinition, String beanName) {
		Assert.notNull(beanDefinition, "bean为空。");

		// 解析bean的依赖属性
		List<Field> fieldList = new ArrayList<>();
		Class<?> clazz = beanDefinition.getBeanClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			AutoInject ai = field.getAnnotation(AutoInject.class);
			if (ai != null) {
				fieldList.add(field);
			}
		}
		if (fieldList.size() > 0) {
			InjectMetadata im = new InjectMetadata(fieldList, clazz);
			beanDefinition.setInjectMetadata(im);
		}
		
	}

	@Override
	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}

}
