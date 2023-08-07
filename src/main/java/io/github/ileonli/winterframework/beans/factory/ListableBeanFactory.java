package io.github.ileonli.winterframework.beans.factory;

public interface ListableBeanFactory extends BeanFactory {

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanDefinitionNames(Class<?> type);

}
