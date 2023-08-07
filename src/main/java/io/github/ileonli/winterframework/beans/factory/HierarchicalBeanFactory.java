package io.github.ileonli.winterframework.beans.factory;

public interface HierarchicalBeanFactory extends BeanFactory {

    BeanFactory getParentBeanFactory();

}
