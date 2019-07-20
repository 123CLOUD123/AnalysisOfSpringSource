package com.analysis.metadata;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 属性依赖元数据
 * @author Cloud
 *
 */
public class InjectMetadata {

	private final List<Field> fields;
	
	private final Class<?> clazz;
	
	public InjectMetadata(List<Field> fields, Class<?> clazz) {
		this.fields = fields;
		this.clazz = clazz;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	
}
