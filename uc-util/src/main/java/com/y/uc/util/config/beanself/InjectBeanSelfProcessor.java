package com.y.uc.util.config.beanself;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class InjectBeanSelfProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof BeanSelfAware)) {
            return bean;
        }
        if (AopUtils.isAopProxy(bean)) {
            ((BeanSelfAware) bean).setSelf(bean);
        } else {
            ((BeanSelfAware) bean).setSelf(context.getBean(beanName));
        }
        return bean;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
