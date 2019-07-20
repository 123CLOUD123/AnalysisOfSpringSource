package com.analysis.test;

import java.io.File;

import com.analysis.annotation.Config;
import com.analysis.context.AppContext;
import com.analysis.test.test1.Hello;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException {
		
		AppContext a = new AppContext(TestConfig.class);
		Hello h = (Hello) a.getBean("hello");
		h.f("123");
//		System.out.println(new File(".").getAbsolutePath());
//		System.out.println(System.getProperty("user.dir"));
//		File f = new File("./src/com/analysis");
//		
//		String[] ss = f.list();
//		for (String s : ss) {
//			System.out.println(s);
//		}
//		System.out.println(Test.class == Test.class);
//		Class.forName("com.analysis.test.TestConfig");
		

		
		
	}

}
