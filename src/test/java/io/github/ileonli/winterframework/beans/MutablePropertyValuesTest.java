package io.github.ileonli.winterframework.beans;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class MutablePropertyValuesTest {

    private static final int TEST_COUNT = 10;

    @Test
    public void addPropertyValue() {
        MutablePropertyValues pvs = new MutablePropertyValues();
        for (int i = 0; i < TEST_COUNT; i++) {
            pvs.addPropertyValue(String.valueOf(i), i);
        }
        for (int i = 0; i < TEST_COUNT; i++) {
            PropertyValue pv = pvs.getPropertyValue(String.valueOf(i));
            String name = pv.getName();
            Object value = pv.getValue();

            assertEquals(name, String.valueOf(i));
            assertEquals(value, i);
        }

        PropertyValue newPv = new PropertyValue("name", "value");
        pvs.addPropertyValue(newPv);

        assertEquals(pvs.getPropertyValue("name"), newPv);
    }

    @Test
    public void addPropertyValueOfOverride() {
        MutablePropertyValues pvs = new MutablePropertyValues();
        pvs.addPropertyValue(new PropertyValue("first", "first"));
        pvs.addPropertyValue(new PropertyValue("second", "second"));

        pvs.addPropertyValue(new PropertyValue("first", "new first"));
        PropertyValue pv = pvs.getPropertyValue("first");
        assertEquals(pv.getValue(), "new first");

        for (PropertyValue propertyValue : pvs.getPropertyValues()) {
            // PropertyValue("first", "first") should already be removed.
            if (Objects.equals(propertyValue.getName(), "first") &&
                    Objects.equals(propertyValue.getValue(), "first")) {
                throw new RuntimeException("PropertyValue('first', 'first') should already be removed");
            }
        }
    }

    @Test
    public void removePropertyValue() {
        MutablePropertyValues pvs = new MutablePropertyValues();
        for (int i = 0; i < TEST_COUNT; i++) {
            pvs.addPropertyValue(String.valueOf(i), i);
        }
        // remove all PropertyValue with even number name
        for (int i = 0; i < TEST_COUNT; i += 2) {
            PropertyValue pv = pvs.getPropertyValue(String.valueOf(i));
            pvs.removePropertyValue(pv);
        }
        for (int i = 0; i < TEST_COUNT; i++) {
            PropertyValue pv = pvs.getPropertyValue(String.valueOf(i));
            if (i % 2 == 0) {
                assertNull(pv);
            } else {
                assertNotNull(pv);
                assertEquals(pv.getName(), String.valueOf(i));
            }
        }
    }

    @Test
    public void setPropertyValueAt() {
        MutablePropertyValues pvs = new MutablePropertyValues();
        for (int i = 0; i < TEST_COUNT; i++) {
            pvs.addPropertyValue(String.valueOf(i), i);
        }
        for (int i = 0; i < TEST_COUNT; i++) {
            int newVal = i + 100;
            pvs.setPropertyValueAt(i, new PropertyValue(String.valueOf(newVal), newVal));
        }
        for (int i = 0; i < TEST_COUNT; i++) {
            int val = i + 100;
            PropertyValue pv = pvs.getPropertyValue(String.valueOf(val));
            String name = pv.getName();
            Object value = pv.getValue();

            assertEquals(name, String.valueOf(value));
            assertEquals(value, val);
        }
    }

    @Test
    public void getPropertyValues() {
        MutablePropertyValues pvs = new MutablePropertyValues();
        PropertyValue[] pv1 = pvs.getPropertyValues();
        assertNotNull(pv1);

        for (int i = 0; i < TEST_COUNT; i++) {
            pvs.addPropertyValue(String.valueOf(i), i);
        }

        PropertyValue[] pv2 = pvs.getPropertyValues();
        assertNotNull(pv2);
        assertEquals(pv2.length, TEST_COUNT);
    }

    @Test
    public void getPropertyValue() {
        MutablePropertyValues pvs = new MutablePropertyValues();
        for (int i = 0; i < TEST_COUNT; i++) {
            pvs.addPropertyValue(String.valueOf(i), i);
        }
        for (int i = 0; i < TEST_COUNT; i++) {
            PropertyValue pv = pvs.getPropertyValue(String.valueOf(i));
            String name = pv.getName();
            Object value = pv.getValue();

            assertEquals(name, String.valueOf(i));
            assertEquals(value, i);
        }
    }

    @Test
    public void contains() {
        MutablePropertyValues pvs = new MutablePropertyValues();
        for (int i = 0; i < TEST_COUNT; i++) {
            pvs.addPropertyValue(String.valueOf(i), i);
        }
        for (int i = 0; i < TEST_COUNT; i++) {
            assertTrue(pvs.contains(String.valueOf(i)));
            assertFalse(pvs.contains(String.valueOf(i + TEST_COUNT)));
        }
    }

    @Test
    public void changesSinceOfEmpty() {
        MutablePropertyValues pvs1 = new MutablePropertyValues();
        pvs1.addPropertyValue(new PropertyValue("forname", "Tony"));
        pvs1.addPropertyValue(new PropertyValue("surname", "Blair"));
        pvs1.addPropertyValue(new PropertyValue("age", "50"));

        MutablePropertyValues pvs2 = new MutablePropertyValues();
        PropertyValues changes = pvs2.changesSince(pvs1);

        assertEquals(0, changes.getPropertyValues().length);
    }

    @Test
    public void changesSinceOfOneField() {
        MutablePropertyValues pvs1 = new MutablePropertyValues();
        pvs1.addPropertyValue(new PropertyValue("forname", "Tony"));
        pvs1.addPropertyValue(new PropertyValue("surname", "Blair"));
        pvs1.addPropertyValue(new PropertyValue("age", "50"));

        MutablePropertyValues pvs2 = new MutablePropertyValues(pvs1);
        PropertyValues changes1 = pvs2.changesSince(pvs1);
        assertEquals(0, changes1.getPropertyValues().length);

        pvs2.addPropertyValue(new PropertyValue("forname", "Gordon"));
        PropertyValues changes2 = pvs2.changesSince(pvs1);
        assertEquals(1, changes2.getPropertyValues().length);

        PropertyValue fn = changes2.getPropertyValue("forname");
        assertEquals("Gordon", fn.getValue());

        MutablePropertyValues pvs3 = new MutablePropertyValues(pvs1);
        changes2 = pvs3.changesSince(pvs1);
        assertEquals(0, changes2.getPropertyValues().length);

        pvs3.addPropertyValue(new PropertyValue("foo", "bar"));
        pvs3.addPropertyValue(new PropertyValue("fi", "fum"));
        changes2 = pvs3.changesSince(pvs1);
        assertEquals(2, changes2.getPropertyValues().length);

        fn = changes2.getPropertyValue("foo");
        assertNotNull(fn);
        assertEquals("bar", fn.getValue());
    }
}