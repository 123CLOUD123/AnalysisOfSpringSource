package com.analysis.support;

import com.analysis.context.BeanFactory;

/**
 * 用来获取对BeanFactory的引用
 * @author Cloud
 *
 */
public interface BeanFactoryAware {

	void setFactory(BeanFactory factory);
}
