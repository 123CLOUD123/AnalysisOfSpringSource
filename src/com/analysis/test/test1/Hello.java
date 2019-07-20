package com.analysis.test.test1;

import com.analysis.annotation.AutoInject;
import com.analysis.annotation.Part;
import com.analysis.processorinterface.AfterInstantiatedPostProcessor;

@Part
public class Hello implements AfterInstantiatedPostProcessor{
	
	@AutoInject
	private World world;
	
	@AutoInject
	private HelloJAVA hj;

	@Override
	public void afterSingletonsInstantiated() {
		// TODO Auto-generated method stub
		System.out.println("hello world !!!!!!");
	}
	
	public void f(String mes) {
		System.out.println("hello " + mes + world.f("45678") + hj.f());
	}
}
