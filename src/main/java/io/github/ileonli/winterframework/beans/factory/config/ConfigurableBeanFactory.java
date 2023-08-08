package io.github.ileonli.winterframework.beans.factory.config;

import io.github.ileonli.winterframework.beans.BeanException;
import io.github.ileonli.winterframework.beans.factory.BeanFactory;
import io.github.ileonli.winterframework.beans.factory.HierarchicalBeanFactory;

import java.beans.PropertyEditor;


public interface ConfigurableBeanFactory extends HierarchicalBeanFactory {

    void setParentBeanFactory(BeanFactory parentBeanFactory);

    void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor);

    void ignoreDependencyType(Class<?> type);

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    void registerAlias(String beanName, String alias) throws BeanException;

    void registerSingleton(String beanName, Object singletonObject) throws BeanException;

    void destroySingletons();

}
