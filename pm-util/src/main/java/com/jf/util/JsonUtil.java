package com.jf.util;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

import java.util.Date;

/**
 * Json处理类
 * 
 * @author flyChan
 * 
 */
public class JsonUtil {

	/**
	 * 获取完整信息序列化类
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static JSONSerializer getJSONSerializer(String dateFormat) {
		JSONSerializer serializer = new JSONSerializer();
		serializer.exclude(new String[] { "*.class" });
		serializer.exclude(new String[] { "*.handler",
				"*.hibernateLazyInitializer" });
		serializer.transform(new DateTransformer(dateFormat),
				new Class[] { Date.class });
		return serializer;
	}

	public static JSONSerializer getJSONSerializer() {
		JSONSerializer serializer = new JSONSerializer();
		serializer.exclude(new String[] { "*.class" });
		serializer.exclude(new String[] { "*.handler",
				"*.hibernateLazyInitializer" });
		return serializer;
	}

	/**
	 * 获取包含指定字段，排除指定字段序列化类
	 * 
	 * @param includeRefProperties
	 * @param excludeProperties
	 * @param dateFormat
	 * @return
	 */
	public static JSONSerializer getJSONSerializer(
			String[] includeRefProperties, String[] excludeProperties,
			String dateFormat) {
		JSONSerializer serializer = new JSONSerializer();
		serializer.include(includeRefProperties);
		serializer.exclude(excludeProperties);
		serializer.exclude(new String[] { "*.class" });
		serializer.exclude(new String[] { "*.handler",
				"*.hibernateLazyInitializer" });
		serializer.transform(new DateTransformer(dateFormat),
				new Class[] { Date.class });
		return serializer;
	}

	public static JSONSerializer getJSONSerializer(
			String[] includeRefProperties, String[] excludeProperties) {
		JSONSerializer serializer = new JSONSerializer();
		serializer.include(includeRefProperties);
		serializer.exclude(excludeProperties);
		serializer.exclude(new String[] { "*.class" });
		serializer.exclude(new String[] { "*.handler",
				"*.hibernateLazyInitializer" });
		return serializer;
	}

	public static String genJson(Object object) {
		JSONSerializer serializer = new JSONSerializer();
		serializer.exclude(new String[] { "*.class" });
		serializer.exclude(new String[] { "*.handler",
				"*.hibernateLazyInitializer" });
		return serializer.serialize(object);
	}
}
