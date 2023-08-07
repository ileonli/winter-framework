package io.github.ileonli.winterframework.beans.factory;

import io.github.ileonli.winterframework.beans.BeanException;

public interface BeanFactory {

    Object getBean(String name) throws BeanException;

    Object getBean(String name, Class<?> requiredType) throws BeanException;

    boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

    String[] getAliases(String name);

}
