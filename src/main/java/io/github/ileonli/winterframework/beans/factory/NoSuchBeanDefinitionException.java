package io.github.ileonli.winterframework.beans.factory;

import io.github.ileonli.winterframework.beans.BeanException;

public class NoSuchBeanDefinitionException extends BeanException {

    private final String name;

    public NoSuchBeanDefinitionException(String name) {
        super("No bean named [" + name + "] is defined");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
