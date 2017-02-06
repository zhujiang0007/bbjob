package com.rundatop.core.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rundatop.core.exception.BizRuntimeException;
import com.rundatop.core.spring.annotation.Config;
import com.rundatop.core.utils.Utils;
import com.rundatop.core.utils.parse.json.JsonParser;

/**
 * 类功能说明：通用数据上下文基类,内置了JSON格式的报文转换成Map结构化数据的功能
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
public class CommContext {
	public final static String DS_KEY = "$$DS_KEY";
	protected static final String XML_ROOT = "ROOT";
	
	/**
	 * data对象可能传入的类型有：String (JSON),Map 两种
	 */
	protected Object data = null;
	/**
	 * 0:HashMap; 1:JSON_String;
	 * 
	 */
	protected Object[] cache = new Object[2];

	public CommContext() {
		this.data = new HashMap();
	}

	public CommContext(Object data) {
		this.data = data;
	}

	public Object get(Object key) {
		return getMap().get(key);
	}

	public Object put(Object key, Object value) {
		return getMap().put(key, value);
	}

	public Map getMap() {
		if (cache[0] != null) {
			return (Map) cache[0];
		}
		if (data instanceof String) {
			cache[0] = JsonParser.getMapFromJson((String) data);

		} else if (data instanceof Map) {
			cache[0] = (Map) data;
		} else {
			throw new BizRuntimeException(data.getClass().getClass()
					+ "该类型对象不支持！");
		}
		return (Map) cache[0];
	}

	protected String getXmlFromMap(Map data) {
		StringBuffer builder = new StringBuffer();
		for (Map.Entry entry : (Set<Map.Entry>) data.entrySet()) {
			Object val = entry.getValue();
			if (val == null) {
				String key = entry.getKey().toString();
				builder.append("<").append(key).append("/>");
				continue;
			} else if (val instanceof Map) {
				Map map = (Map) val;
				String key = entry.getKey().toString();
				builder.append("<").append(key).append(">")
						.append(getXmlFromMap(map)).append("</").append(key)
						.append(">");
			} else if (val instanceof List) {
				String key = entry.getKey().toString();
				builder.append("<").append(key).append(">");
				List<Map> list = (List) val;
				for (Map row : list) {
					builder.append("<ITEM>");
					builder.append(getXmlFromMap(row));
					builder.append("</ITEM>");
				}
				builder.append("</").append(key).append(">");
			} else {
				String key = entry.getKey().toString();
				builder.append("<").append(key).append(">")
						.append(Utils.NORMAL2XML(val.toString())).append("</")
						.append(key).append(">");
			}
		}
		return builder.toString();
	}

	public String toString() {
		return getJson();
	}

	public void clear() {
		cache = new Object[2];
		data = new HashMap();
	}

	public String getJson() {
		return JsonParser.getJsonFromMap(this.getMap());
	}

	public static void main(String[] args) {

	}
}
