package io.github.ileonli.winterframework.beans.factory.support;

import io.github.ileonli.winterframework.beans.MutablePropertyValues;
import io.github.ileonli.winterframework.beans.factory.FactoryBean;
import io.github.ileonli.winterframework.beans.factory.config.AutowireType;
import io.github.ileonli.winterframework.beans.factory.config.ConstructorArgumentValues;
import io.github.ileonli.winterframework.beans.factory.config.DependencyCheckType;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;

import static io.github.ileonli.winterframework.beans.factory.config.AutowireType.*;
import static io.github.ileonli.winterframework.beans.factory.config.DependencyCheckType.DEPENDENCY_CHECK_NONE;

public class RootBeanDefinition extends AbstractBeanDefinition {

    private Object beanClass;

    private ConstructorArgumentValues constructorArgumentValues;

    private AutowireType autowireType = AUTOWIRE_NO;

    private DependencyCheckType dependencyCheck = DEPENDENCY_CHECK_NONE;

    private String[] dependsOn;

    private String initMethodName;

    private String destroyMethodName;

    protected RootBeanDefinition(MutablePropertyValues pvs) {
        super(pvs);
    }

    public RootBeanDefinition(Class<?> beanClass, AutowireType autowireType) {
        this(null);
        this.beanClass = beanClass;
        this.autowireType = autowireType;
    }


    @Override
    public ConstructorArgumentValues getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    public boolean hasConstructorArgumentValues() {
        return Objects.nonNull(constructorArgumentValues) &&
                !constructorArgumentValues.isEmpty();
    }

    public final Class<?> getBeanClass() throws IllegalStateException {
        if (!(this.beanClass instanceof Class)) {
            throw new IllegalStateException("Bean definition does not carry a resolved bean class");
        }
        return (Class<?>) this.beanClass;
    }

    public final String getBeanClassName() {
        Class<?> aClass = this.getBeanClass();
        if (Objects.isNull(aClass)) {
            return "";
        }
        return aClass.getName();
    }

    public AutowireType getAutowireType() {
        return autowireType;
    }

    public void setAutowireType(AutowireType autowireType) {
        this.autowireType = autowireType;
    }

    public AutowireType getResolvedAutowireMode() {
        if (this.autowireType == AUTOWIRE_AUTODETECT) {
            // Work out whether to apply setter autowiring or constructor autowiring.
            // If it has a no-arg constructor it's deemed to be setter autowiring,
            // otherwise we'll try constructor autowiring.
            Constructor<?>[] constructors = getBeanClass().getConstructors();
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterTypes().length == 0) {
                    return AUTOWIRE_BY_TYPE;
                }
            }
            return AUTOWIRE_CONSTRUCTOR;
        } else {
            return this.autowireType;
        }
    }

    public DependencyCheckType getDependencyCheck() {
        return dependencyCheck;
    }

    public void setDependencyCheck(DependencyCheckType dependencyCheck) {
        this.dependencyCheck = dependencyCheck;
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    @Override
    public void validate() throws BeanDefinitionValidationException {
        super.validate();
        if (this.beanClass == null) {
            throw new BeanDefinitionValidationException("beanClass must be set in RootBeanDefinition");
        }
        if (this.beanClass instanceof Class) {
            if (FactoryBean.class.isAssignableFrom(getBeanClass()) && !isSingleton()) {
                throw new BeanDefinitionValidationException("FactoryBean must be defined as singleton - " +
                        "FactoryBeans themselves are not allowed to be prototypes");
            }
            if (getBeanClass().getConstructors().length == 0) {
                throw new BeanDefinitionValidationException("No public constructor in class [" + getBeanClass() + "]");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RootBeanDefinition that = (RootBeanDefinition) o;
        return Objects.equals(beanClass, that.beanClass) &&
                Objects.equals(constructorArgumentValues, that.constructorArgumentValues) &&
                autowireType == that.autowireType && dependencyCheck == that.dependencyCheck &&
                Arrays.equals(dependsOn, that.dependsOn) &&
                Objects.equals(initMethodName, that.initMethodName) &&
                Objects.equals(destroyMethodName, that.destroyMethodName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(
                beanClass, constructorArgumentValues, autowireType,
                dependencyCheck, initMethodName, destroyMethodName);
        result = 31 * result + Arrays.hashCode(dependsOn);
        return result;
    }

    @Override
    public String toString() {
        return "RootBeanDefinition{" +
                "beanClass=" + beanClass +
                ", constructorArgumentValues=" + constructorArgumentValues +
                ", autowireType=" + autowireType +
                ", dependencyCheck=" + dependencyCheck +
                ", dependsOn=" + Arrays.toString(dependsOn) +
                ", initMethodName='" + initMethodName + '\'' +
                ", destroyMethodName='" + destroyMethodName + '\'' +
                '}';
    }

}
