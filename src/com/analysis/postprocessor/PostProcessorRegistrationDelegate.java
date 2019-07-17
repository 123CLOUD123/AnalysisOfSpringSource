package com.analysis.postprocessor;

import java.util.List;

import com.analysis.context.BeanFactory;

public class PostProcessorRegistrationDelegate {

	public static void invokeBeanFactoryPostProcessors(BeanFactory factory, List<BeanFactoryPostProcessor> processors) {
		
		// 调用已存在的BeanFactoryPostProcessor
		for (BeanFactoryPostProcessor bpp : processors) {
			bpp.process(factory);
		}
		
		
		
	}
	
}
