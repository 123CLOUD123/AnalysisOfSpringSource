package com.analysis.processorinterface;

/**
 * 实例化后接受回调
 * @author Cloud
 *
 */
public interface AfterInstantiatedPostProcessor {

	void afterSingletonsInstantiated();
	
}
