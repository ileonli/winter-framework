package io.github.ileonli.winterframework.beans.factory.support;

import io.github.ileonli.winterframework.beans.BeanException;

public class BeanDefinitionValidationException extends BeanException {
    public BeanDefinitionValidationException() {
    }

    public BeanDefinitionValidationException(String message) {
        super(message);
    }

    public BeanDefinitionValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanDefinitionValidationException(Throwable cause) {
        super(cause);
    }
}
