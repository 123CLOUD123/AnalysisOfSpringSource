package com.analysis.util;

/**
 * bean名称生成类
 * @author Cloud
 *
 */
public class BeanNameGenerator {

	/*
	 * 根据类名生成名称
	 * @param clazz 类
	 * @return 类简单名称首字母小写的字符串
	 */
	public static String getName(Class<?> clazz) {
		String result = clazz.getName();
		result = result.substring(result.lastIndexOf(".") + 1);
		int firstChar = result.charAt(0);
		result = result.replaceFirst(new Character((char) firstChar).toString(), new Character((char) (firstChar + 32)).toString());
		return result;
	}
	
}
