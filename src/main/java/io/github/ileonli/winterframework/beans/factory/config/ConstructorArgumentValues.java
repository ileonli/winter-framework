package io.github.ileonli.winterframework.beans.factory.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConstructorArgumentValues {

    private final Map<Integer, ValueHolder> indexedArgumentValues;

    public ConstructorArgumentValues() {
        this.indexedArgumentValues = new HashMap<>();
    }

    public ConstructorArgumentValues(int argumentsNum) {
        this.indexedArgumentValues = new HashMap<>(argumentsNum);
    }

    public void addIndexedArgumentValue(int index, Object value) {
        this.indexedArgumentValues.put(index, new ValueHolder(value));
    }

    public ValueHolder getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public ValueHolder getIndexedArgumentValue(int index, Class<?> requiredType) {
        ValueHolder valueHolder = this.indexedArgumentValues.get(index);
        if (Objects.isNull(valueHolder)) {
            return null;
        }
        if (!Objects.equals(valueHolder.type, requiredType)) {
            return null;
        }
        return valueHolder;
    }

    public Map<Integer, ValueHolder> getIndexedArgumentValues() {
        return Collections.unmodifiableMap(this.indexedArgumentValues);
    }

    // used to store value and it's type
    public static class ValueHolder {

        private Object value;

        private Class<?> type;

        public ValueHolder(Object value) {
            this(value, value.getClass());
        }

        public ValueHolder(Object value, Class<?> type) {
            this.value = value;
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Class<?> getType() {
            return type;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ValueHolder that = (ValueHolder) o;
            return Objects.equals(value, that.value) && Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, type);
        }

        @Override
        public String toString() {
            return "ValueHolder{" +
                    "value=" + value +
                    ", type=" + type +
                    '}';
        }
    }
}
