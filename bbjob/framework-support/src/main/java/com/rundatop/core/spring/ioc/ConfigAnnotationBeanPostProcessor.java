package com.rundatop.core.spring.ioc;

/**
 * 类功能说明  配置BeanPostProcessor
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
 * @date 2014-5-4 上午9:23:28
 * @version V1.0
 */
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeansException;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.util.ReflectionUtils;

import com.rundatop.core.config.SystemConfig;
import com.rundatop.core.spring.annotation.Config;
import com.rundatop.core.spring.config.ExtendedPropertyPlaceholderConfigurer;



public class ConfigAnnotationBeanPostProcessor extends
		InstantiationAwareBeanPostProcessorAdapter {

	private ExtendedPropertyPlaceholderConfigurer propertyConfigurer;
	private SystemConfig systemConfig;
	
	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	/**
	 * @param propertyConfigurer
	 *            the propertyConfigurer to set
	 */
	public void setPropertyConfigurer(
			ExtendedPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}

	// 创建简单类型转换器
	private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

	@Override
	public boolean postProcessAfterInstantiation(final Object bean,
			String beanName) throws BeansException {
		ReflectionUtils.doWithFields(bean.getClass(),
				new ReflectionUtils.FieldCallback() {
					public void doWith(Field field)
							throws IllegalArgumentException,
							IllegalAccessException {
						Config cfg = field.getAnnotation(Config.class);
						if (cfg != null) {
							if (Modifier.isStatic(field.getModifiers())) {
								throw new IllegalStateException(
										"@Config annotation is not supported  on static fields");
							}
							// 如果开发者没有设置@Config的 value，则使用变量域的名称作为键查找配置资源
							String key = cfg.value().length() <= 0 ? field
									.getName() : cfg.value();
							Object value = propertyConfigurer.getProperty(key);

							if (value != null) {
								// 转换配置值成其它非String类型
								Object _value = typeConverter
										.convertIfNecessary(value,
												field.getType());
								// 使变量域可用，并且转换后的配置值注入其中
								ReflectionUtils.makeAccessible(field);
								field.set(bean, _value);
							}
						}
					}
				});
		// 通常情况下返回true即可
		return true;
	}
}
