package io.github.ileonli.winterframework.beans.factory.support;

import io.github.ileonli.winterframework.beans.MutablePropertyValues;
import io.github.ileonli.winterframework.beans.factory.config.BeanDefinition;
import io.github.ileonli.winterframework.beans.factory.config.ConstructorArgumentValues;

import java.util.Objects;

public abstract class AbstractBeanDefinition implements BeanDefinition {

    private MutablePropertyValues propertyValues;

    private String resourceDescription;

    private boolean singleton = true;

    private boolean lazyInit = false;

    protected AbstractBeanDefinition(MutablePropertyValues pvs) {
        this.propertyValues = Objects.requireNonNullElse(pvs, new MutablePropertyValues());
    }

    @Override
    public MutablePropertyValues getPropertyValues() {
        return propertyValues;
    }

    /**
     * Just RootBeanDefinitions have concrete support
     * for constructor argument values
     *
     * @return null
     */
    public ConstructorArgumentValues getConstructorArgumentValues() {
        return null;
    }

    @Override
    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void validate() throws BeanDefinitionValidationException {
        if (this.lazyInit && !this.singleton) {
            throw new BeanDefinitionValidationException("Lazy initialization is just applicable to singleton beans");
        }
    }

}
