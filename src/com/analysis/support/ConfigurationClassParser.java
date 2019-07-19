package com.analysis.support;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.analysis.annotation.Config;
import com.analysis.annotation.Part;
import com.analysis.bean.BeanDefinition;
import com.analysis.bean.BeanDefinitionHolder;
import com.analysis.context.BeanFactory;
import com.analysis.metadata.AnnotationMetadata;
import com.analysis.util.BeanNameGenerator;

public class ConfigurationClassParser {
	
	private Logger log = Logger.getLogger(getClass().getName());

	private BeanFactory factory;
	
	public ConfigurationClassParser(BeanFactory factory) {
		this.factory = factory;
	}
	
	public void parse(List<BeanDefinitionHolder> bdhs) {
		
		// 解析需要扫描的包
		List<String> scanPackages = new ArrayList<>();
		for (BeanDefinitionHolder bdh : bdhs) {
			BeanDefinition bd = bdh.getBeanDefinition();
			AnnotationMetadata am = bd.getAnnotationMetadata();
			Annotation[] as = am.getAnnotations();
			for (Annotation a : as) {
				if (a.annotationType().getSimpleName().equals("Config")) {
					Config config = (Config) a;
					String[] packages = config.packages();
					for (String pack : packages) {
						scanPackages.add(pack);
					}
				}
			}
		}
		
		// 若包为空，则返回
		if (scanPackages.size() == 0) {
			return;
		}
		
		// 根据包解析文件
		List<String> javaFiles = new ArrayList<>();
		String base = "src/";
		for (String pack : scanPackages) {
			String processedPack = base + pack;
			File f = new File(processedPack);
			String[] ss = f.list((dir, filename) -> {
				if (!filename.contains(".") && !filename.substring(filename.lastIndexOf(".")).equals(".java")) {
					return false;
				} else {
					String shortName = filename.substring(0, filename.indexOf("."));
					String fullName = (pack + "." + shortName).replace("/", ".");
					try {
						Class<?> clazz = Class.forName(fullName);
						Annotation anno = clazz.getAnnotation(Part.class);
						if (anno == null) {
							return false;
						} else {
							return true;
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					return false;
				}
			});
			for (String s : ss) {
				s = s.substring(0, s.indexOf("."));
				String absoluteFile = pack + "/" + s;
				absoluteFile = absoluteFile.replace("/", ".");
				javaFiles.add(absoluteFile);
			}
		}
		
		// 把解析到的类注册到beanFactory中
		for (String javaFile : javaFiles) {
			try {
				Class<?> clazz = Class.forName(javaFile);
				BeanDefinition bd = new BeanDefinition(clazz);
				String beanName = BeanNameGenerator.getName(clazz);
				factory.registerBeanDefinition(beanName, bd);
			} catch (ClassNotFoundException e) {
				log.warning("类加载失败，未找到类" + javaFile);
			}
		}
		
	}
	
}
