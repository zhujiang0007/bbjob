package com.rundatop.core.spring.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.util.ReflectionUtils;

import com.rundatop.core.spring.ApplicationContext;
import com.rundatop.core.spring.annotation.Listener;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

public class ListenerAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter{

	private ApplicationContext context;
	public ApplicationContext getContext() {
		return context;
	}
	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		ReflectionUtils.doWithFields(bean.getClass(),new ReflectionUtils.FieldCallback(){

			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				
				Listener listener=field.getAnnotation(Listener.class);
				if(listener==null)
					return;
				
				if (Modifier.isStatic(field.getModifiers())) {
					throw new IllegalStateException(
							"@Config annotation is not supported  on static fields");
				}
				if(!field.getType().isAssignableFrom(List.class)){
					throw new IllegalStateException(
							"@Listener annotation is not supported  on not inherited from List field");
				}
				String[] names=listener.name();
				ReflectionUtils.makeAccessible(field);
				List a=(List) field.get(bean);
				if(a==null)
					a=new ArrayList();
				if(names==null||names.length==0){
					Type gennericType=field.getGenericType();
					if(gennericType instanceof ParameterizedType){
						 ParameterizedType parameterizedType = (ParameterizedType) gennericType;
						 Type[] actualTypeArg= parameterizedType.getActualTypeArguments();
						 if(actualTypeArg[0] instanceof Class){
							 Class<?> genTyle=(Class<?>)actualTypeArg[0];
							 names=context.getBeanNamesForType(genTyle);
						 }else{
							 ParameterizedTypeImpl paramType=(ParameterizedTypeImpl)actualTypeArg[0];
							 Class<?> genTyle=paramType.getRawType();
							 names=context.getBeanNamesForType(genTyle);
						 }
						
					}
					
				}
				for(String beanName:names){
					Object o= context.getBean(beanName);
					a.add(o);
				}
				field.set(bean, a);
			}
			
		});
		return true;
	}
}
