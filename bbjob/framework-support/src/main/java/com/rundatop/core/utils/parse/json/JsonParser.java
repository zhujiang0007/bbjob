package com.rundatop.core.utils.parse.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 类功能说明：JSON格式数据处理解析类
 * <p>
 * 类修改者
 * <p>
 * 修改日期
 * <p>
 * 修改说明
 * <p>
 * Copyright: Copyright (c) 2014
 * <p>
 * Company:广联达软件股份有限公司
 * 
 * @author pus-a
 * @date 2014-4-18 下午1:28:34
 * @version V1.0
 */
public class JsonParser {
	public static ObjectMapper objectMapper = new ObjectMapper();
	static {
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper
				.setSerializationInclusion(Include.NON_DEFAULT);
		objectMapper
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
		// objectMapper.getSerializerProvider().setNullKeySerializer(
		// new JsonSerializer<Object>() {
		//
		// @Override
		// public void serialize(Object value, JsonGenerator jg,
		// SerializerProvider sp) throws IOException,
		// JsonProcessingException {
		// jg.writeString("");
		// }
		//
		// });
		objectMapper.getSerializerProvider().setNullValueSerializer(
				new JsonSerializer<Object>() {
					@Override
					public void serialize(Object value, JsonGenerator jg,
							SerializerProvider sp) throws IOException,
							JsonProcessingException {
						if (value instanceof String) {
							jg.writeString("");
						} else {
							jg.writeObject(value);
						}
					}
				});
		// null值不参与序列化
		objectMapper
				.setSerializationInclusion(Include.NON_NULL);
		// 默认值不参与序列化
		objectMapper
				.setSerializationInclusion(Include.NON_DEFAULT);

	}

	public static List getListFromJson(String json) {
		List l = new ArrayList();
		try {
			l = objectMapper.readValue(json, List.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return l;
	}

	/**
	 * 如果对象为Null,返回"null". 如果集合为空集合,返回"[]".
	 */
	public static String pojotoJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (IOException e) {
			return object.toString();
			
		}
	}

	public static Object getObjectFromJson(String json, Class clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Map getMapFromJson(String json) {

		Map map = new HashMap();
		try {
			map = objectMapper.readValue(json, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return map;
	}

	public static String getJsonFromMap(Map map) {
		try {
			return objectMapper.writeValueAsString(map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static String getJsonFromObject(Object obj) {

		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
