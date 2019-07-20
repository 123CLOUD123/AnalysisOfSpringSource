package com.analysis.test.test1;

import com.analysis.annotation.AutoInject;
import com.analysis.annotation.Part;

@Part
public class World {

	@AutoInject
	HelloJAVA h;
	
	public String f(String mes) {
		return h.f();
	}
}
