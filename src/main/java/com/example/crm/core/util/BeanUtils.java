package com.example.crm.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils implements ApplicationContextAware
{
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext _applicationContext) throws BeansException
	{
		applicationContext = _applicationContext;
	}

	public static Object findBean(String beanName)
	{
		return applicationContext.getBean(beanName);
	}

}
