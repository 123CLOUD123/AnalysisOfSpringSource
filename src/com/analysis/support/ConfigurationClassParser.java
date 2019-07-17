package com.analysis.support;

import java.lang.annotation.Annotation;
import java.util.List;

import com.analysis.annotation.Config;
import com.analysis.bean.BeanDefinition;
import com.analysis.bean.BeanDefinitionHolder;
import com.analysis.context.BeanFactory;
import com.analysis.metadata.AnnotationMetadata;

public class ConfigurationClassParser {

	private BeanFactory factory;
	
	public ConfigurationClassParser(BeanFactory factory) {
		this.factory = factory;
	}
	
	public void parse(List<BeanDefinitionHolder> bdhs) {
		List<String> scanPackages = new ArrayList<>();
		for (BeanDefinitionHolder bdh : bdhs) {
			BeanDefinition bd = bdh.getBeanDefinition();
			AnnotationMetadata am = bd.getAnnotationMetadata();
			Annotation[] as = am.getAnnotations();
			for (Annotation a : as) {
				if (a.getClass().getSimpleName().equals("Config")) {
					Config config = (Config) a;
					String[] packages = config.packages();
				}
			}
		}
	}
	
}
