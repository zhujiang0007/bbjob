package com.rundatop.core.spring.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.rundatop.core.exception.BizRuntimeException;



/**
 * 类功能说明 扩展PropertyPlaceholderConfigurer
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
 * @date 2014-5-4 上午9:30:06
 * @version V1.0
 */
public class ExtendedPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	private static Properties props = new Properties();
	private String[] locations;

	/**
	 * @return the locations
	 */
	public String[] getLocations() {
		return locations;
	}

	/**
	 * @param locations
	 *            the locations to set
	 */
	public void setLocations(String[] locations) {
		this.locations = locations;
	}

	private PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);
		this.props.putAll(props);
	}

	public Object getProperty(String key) {
		return props.get(key);
	}

	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		try {
			preProcessPropertyPlaceholder();
		} catch (IOException e) {
			throw new BizRuntimeException(e);
		}

		super.postProcessBeanFactory(beanFactory);
	}

	private void preProcessPropertyPlaceholder() throws IOException {
		ArrayList<Resource> l = new ArrayList<Resource>();
		for (String location : this.locations) {
			Resource[] resources = patternResolver.getResources(location);
			System.out.println("location:"+location);
			for (Resource resource : resources) {
				Properties tmp = new Properties();
				tmp.load(resource.getInputStream());
				l.add(resource);
				props.putAll(tmp);
			}
		}
		setLocations(l.toArray(new Resource[] {}));
	}
}
