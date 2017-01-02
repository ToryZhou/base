package com.example.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static Object getBeanByName(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public static <T> T getBeanByClass(Class<T> type) throws BeansException {
        return applicationContext.getBean(type);
    }
}
