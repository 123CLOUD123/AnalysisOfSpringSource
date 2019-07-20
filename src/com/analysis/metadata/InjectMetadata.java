package com.analysis.metadata;

import java.lang.reflect.Field;

/**
 * 属性依赖元数据
 * @author Cloud
 *
 */
public class InjectMetadata {

	private final Field[] fields;
	
	private final Class<?> clazz;
	
	public InjectMetadata(Field[] fields, Class<?> clazz) {
		this.fields = fields;
		this.clazz = clazz;
	}
	
	public Field[] getFields() {
		return fields;
	}
	
}
