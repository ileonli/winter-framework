package io.github.ileonli.winterframework.beans.factory.config;

import io.github.ileonli.winterframework.beans.MutablePropertyValues;

public interface BeanDefinition {

    MutablePropertyValues getPropertyValues();

    ConstructorArgumentValues getConstructorArgumentValues();

    String getResourceDescription();

}
