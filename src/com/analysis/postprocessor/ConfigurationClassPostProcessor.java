package com.analysis.postprocessor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.analysis.bean.BeanDefinition;
import com.analysis.bean.BeanDefinitionHolder;
import com.analysis.context.BeanFactory;
import com.analysis.metadata.AnnotationMetadata;
import com.analysis.support.ConfigurationClassParser;

public class ConfigurationClassPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void process(BeanFactory factory) {
		
		// 找出配置类
		List<String> beanNames = factory.getBeanNames();
		List<BeanDefinitionHolder> configBeans = new ArrayList<>();
		for (String beanName : beanNames) {
			BeanDefinition bd = factory.getBeanDefinition(beanName);
			AnnotationMetadata am = bd.getAnnotationMetadata();
			Annotation[] as = am.getAnnotations();
			for (Annotation a : as) {
				String aa = a.annotationType().getSimpleName();
				if (a.annotationType().getSimpleName().equals("Config")) {
					BeanDefinitionHolder bdh = new BeanDefinitionHolder(beanName, bd);
					configBeans.add(bdh);
				}
			}
		}
		
		// 若没有配置类，则返回
		if (configBeans.isEmpty()) {
			return;
		}
		
		// 对配置类进行解析
		ConfigurationClassParser parser = new ConfigurationClassParser(factory);
		parser.parse(configBeans);
		
		
		
	}

}
