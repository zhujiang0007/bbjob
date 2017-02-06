package com.rundatop.core.aop;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;

/**
 * 
 * 类功能说明 AOP代理处理器，简化AOP的繁琐配置
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
 * @date 2014-8-6 下午1:34:44
 * @version V1.0
 */
public class AspectJBeanFactoryPostProcessor implements
		BeanFactoryPostProcessor, Ordered, InitializingBean {
	private static final long serialVersionUID = 1L;
	private int order = Ordered.HIGHEST_PRECEDENCE;
	private String adviceBeanName;
	private String[] pointcutExpressions;

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.adviceBeanName, "adviceBeanName required");
		Assert.notNull(this.pointcutExpressions, "pointcutExpressions required");
	}

	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (beanFactory instanceof BeanDefinitionRegistry) {
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
			for (String expression : this.pointcutExpressions) {
				RootBeanDefinition advisorDefinition = new RootBeanDefinition(
						DefaultBeanFactoryPointcutAdvisor.class);
				advisorDefinition.getPropertyValues().addPropertyValue(
						"adviceBeanName",
						new RuntimeBeanNameReference(this.adviceBeanName));
				String beanName = BeanDefinitionReaderUtils.generateBeanName(
						advisorDefinition, registry, false);
				RootBeanDefinition pointcutDefinition = new RootBeanDefinition(
						ProxyAspectJExpressionPointcut.class);
				pointcutDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
				pointcutDefinition.setSynthetic(true);
				pointcutDefinition.getPropertyValues().addPropertyValue(
						"expression", expression);
				advisorDefinition.getPropertyValues().addPropertyValue(
						"pointcut", pointcutDefinition);
				BeanDefinitionReaderUtils.generateBeanName(pointcutDefinition,
						registry, false);
				registry.registerBeanDefinition(beanName, advisorDefinition);
			}
		}
	}

	public void setAdviceBeanName(String adviceBeanName) {
		this.adviceBeanName = adviceBeanName;
	}

	public void setPointcutExpressions(String[] pointcutExpressions) {
		this.pointcutExpressions = pointcutExpressions;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return this.order;
	}

	public static class ProxyAspectJExpressionPointcut extends
			AspectJExpressionPointcut {
		private static Log log = LogFactory
				.getLog(ProxyAspectJExpressionPointcut.class);
		private static final long serialVersionUID = 1L;
		private ClassFilter classFilter;
		private MethodMatcher methodMatcher;

		public ClassFilter getClassFilter() {
			if (this.classFilter == null) {
				try {
					this.classFilter = super.getClassFilter();
				} catch (Throwable e) {
					this.classFilter = new ClassFilter() {
						@SuppressWarnings("unchecked")
						public boolean matches(Class clazz) {
							return false;
						}
					};
					log.warn("Failed to getClassFilter. the Expression is '"
							+ this.getExpression(), e);
				}
			}
			return this.classFilter;
		}

		public MethodMatcher getMethodMatcher() {
			if (this.methodMatcher == null) {
				try {
					this.methodMatcher = super.getMethodMatcher();
				} catch (Throwable e) {
					this.methodMatcher = new MethodMatcher() {
						public boolean isRuntime() {
							return false;
						}

						@SuppressWarnings("unchecked")
						public boolean matches(Method method, Class targetClass) {
							return false;
						}

						@SuppressWarnings("unchecked")
						public boolean matches(Method method,
								Class targetClass, Object[] args) {
							return false;
						}
					};
					log.warn("Failed to getMethodMatcher. the Expression is '"
							+ this.getExpression(), e);
				}
			}
			return this.methodMatcher;
		}

	}

}