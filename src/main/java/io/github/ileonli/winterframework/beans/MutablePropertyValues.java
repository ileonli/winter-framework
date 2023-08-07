package io.github.ileonli.winterframework.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MutablePropertyValues implements PropertyValues {

    private final List<PropertyValue> propertyValues;

    public MutablePropertyValues() {
        this.propertyValues = new ArrayList<>();
    }

    public MutablePropertyValues(MutablePropertyValues other) {
        int len = Objects.isNull(other) ? 0 : other.propertyValues.size();
        this.propertyValues = new ArrayList<>(len);
        if (len == 0) {
            return;
        }
        PropertyValue[] pvs = other.getPropertyValues();
        for (PropertyValue pv : pvs) {
            addPropertyValue(pv);
        }
    }

    public MutablePropertyValues(Map<String, Object> map) {
        int len = Objects.isNull(map) ? 0 : map.size();
        this.propertyValues = new ArrayList<>(len);
        if (len == 0) {
            return;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String properValueName = entry.getKey();
            Object propertyValue = entry.getValue();
            addPropertyValue(properValueName, propertyValue);
        }
    }

    public void addPropertyValue(PropertyValue newPropertyValue) {
        List<PropertyValue> pvs = this.propertyValues;
        for (int i = 0; i < pvs.size(); i++) {
            PropertyValue oldPropertyValue = pvs.get(i);
            String oldName = oldPropertyValue.getName();
            String newName = newPropertyValue.getName();
            // replace old PropertyValue
            if (Objects.equals(oldName, newName)) {
                pvs.set(i, newPropertyValue);
                return;
            }
        }
        pvs.add(newPropertyValue);
    }

    public void addPropertyValue(String propertyName, Object propertyValue) {
        addPropertyValue(new PropertyValue(propertyName, propertyValue));
    }

    public boolean removePropertyValue(PropertyValue pv) {
        return this.propertyValues.remove(pv);
    }

    public void setPropertyValueAt(int idx, PropertyValue pv) {
        this.propertyValues.set(idx, pv);
    }

    @Override
    public PropertyValue[] getPropertyValues() {
        return this.propertyValues.toArray(new PropertyValue[0]);
    }

    @Override
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValues) {
            String name = pv.getName();
            if (name.equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

    @Override
    public boolean contains(String propertyName) {
        return this.getPropertyValue(propertyName) != null;
    }

    @Override
    public PropertyValues changesSince(PropertyValues old) {
        MutablePropertyValues changes = new MutablePropertyValues();
        if (old == this) {
            return changes;
        }

        for (PropertyValue newPv : this.propertyValues) {
            PropertyValue oldPv = old.getPropertyValue(newPv.getName());
            if (Objects.isNull(oldPv) || !Objects.equals(newPv, oldPv)) {
                changes.addPropertyValue(newPv);
            }
        }
        return changes;
    }

    @Override
    public String toString() {
        return "MutablePropertyValues{" +
                "propertyValues=" + propertyValues +
                '}';
    }

}
