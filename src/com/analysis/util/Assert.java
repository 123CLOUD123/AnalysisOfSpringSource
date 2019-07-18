package com.analysis.util;

/**
 * 工具类
 * @author Cloud
 *
 */
public class Assert {

	/*
	 * 判断是否为空引用
	 */
	public static void notNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	
}
