package io.github.ileonli.winterframework.beans.factory;

/**
 * 如果一个bean实现了这个接口，那么它将被用作工厂，而不是直接作为bean使用。
 */
public interface FactoryBean {

    Object getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();

}
