package io.github.ileonli.winterframework.beans.factory.config;

import io.github.ileonli.winterframework.beans.BeanException;

public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String name) throws BeanException;

    Object postProcessAfterInitialization(Object bean, String name) throws BeanException;

}
