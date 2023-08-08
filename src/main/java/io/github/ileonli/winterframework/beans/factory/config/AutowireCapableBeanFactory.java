package io.github.ileonli.winterframework.beans.factory.config;

import io.github.ileonli.winterframework.beans.BeanException;
import io.github.ileonli.winterframework.beans.factory.BeanFactory;

/**
 * @see AutowireType
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 在 Spring 容器中自动装配一个指定类的对象
     *
     * @param beanClass       指定要自动装配的类的类型
     * @param autowireMode    指定自动装配的模式 AutowireType
     * @param dependencyCheck 指定是否进行依赖检查，如果为 true，则会检查所有依赖是否都已经注册到容器中
     * @return 实例化后的 bean
     * @throws BeanException 实例化失败
     */
    Object autowire(Class<?> beanClass, int autowireMode, boolean dependencyCheck)
            throws BeanException;

    /**
     * 在 Spring 容器中自动装配一个已存在的对象的属性
     *
     * @param existingBean    指定要自动装配的类的类型。
     * @param autowireMode    指定自动装配的模式 AutowireType
     * @param dependencyCheck 指定是否进行依赖检查，如果为 true，则会检查所有依赖是否都已经注册到容器中
     * @throws BeanException 实例化失败
     */
    void autowireBeanProperties(Object existingBean, int autowireMode, boolean dependencyCheck)
            throws BeanException;

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String name)
            throws BeanException;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String name)
            throws BeanException;

}
