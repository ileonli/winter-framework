package io.github.ileonli.winterframework.beans.factory.config;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ConstructorArgumentValuesTest {

    private static final int TEST_COUNT = 16;

    @Test
    public void addIndexedArgumentValue() {
        ConstructorArgumentValues cav = new ConstructorArgumentValues();
        for (int i = 0; i < TEST_COUNT; i++) {
            cav.addIndexedArgumentValue(i, i);
        }
        for (int i = 0; i < TEST_COUNT; i++) {
            ConstructorArgumentValues.ValueHolder vh = cav.getIndexedArgumentValue(i);
            assertEquals(vh.getValue(), i);
            assertEquals(vh.getType(), Integer.class);
        }

        cav.addIndexedArgumentValue(0, "new type");
        ConstructorArgumentValues.ValueHolder vh = cav.getIndexedArgumentValue(0);
        assertEquals(vh.getValue(), "new type");
        assertEquals(vh.getType(), String.class);
    }

    @Test
    public void getIndexedArgumentValue() {
        ConstructorArgumentValues cav = new ConstructorArgumentValues();

        int idx1 = 1;
        cav.addIndexedArgumentValue(idx1, "String");
        ConstructorArgumentValues.ValueHolder holder1 = cav.getIndexedArgumentValue(idx1, String.class);
        assertEquals(holder1.getValue(), "String");
        assertEquals(holder1.getType(), String.class);

        int idx2 = 2;
        cav.addIndexedArgumentValue(idx2, 1);
        ConstructorArgumentValues.ValueHolder holder2 = cav.getIndexedArgumentValue(idx2, Integer.class);
        assertEquals(holder2.getValue(), 1);
        assertEquals(holder2.getType(), Integer.class);

        int idx3 = 3;
        Object obj = new Object();
        cav.addIndexedArgumentValue(idx3, obj);
        ConstructorArgumentValues.ValueHolder holder3 = cav.getIndexedArgumentValue(idx3, Object.class);
        assertEquals(holder3.getValue(), obj);
        assertEquals(holder3.getType(), Object.class);

        // required type is not match, ValueHolder should be null
        int idx4 = 4;
        cav.addIndexedArgumentValue(idx4, 1);
        ConstructorArgumentValues.ValueHolder holder4 = cav.getIndexedArgumentValue(idx4, String.class);
        assertNull(holder4);
    }

    @Test
    public void getIndexedArgumentValues() {
        ConstructorArgumentValues cav = new ConstructorArgumentValues();

        Map<Integer, ConstructorArgumentValues.ValueHolder> map1 = cav.getIndexedArgumentValues();
        assertEquals(map1.size(), 0);

        for (int i = 0; i < TEST_COUNT; i++) {
            cav.addIndexedArgumentValue(i, i);
        }

        Map<Integer, ConstructorArgumentValues.ValueHolder> map2 = cav.getIndexedArgumentValues();
        assertEquals(map2.size(), TEST_COUNT);
    }

    @Test
    public void valueHolder() {
        ConstructorArgumentValues.ValueHolder holder1 =
                new ConstructorArgumentValues.ValueHolder("string");
        assertEquals(holder1.getValue(), "string");
        assertEquals(holder1.getType(), String.class);

        ConstructorArgumentValues.ValueHolder holder2 =
                new ConstructorArgumentValues.ValueHolder(10);
        assertEquals(holder2.getValue(), 10);
        assertEquals(holder2.getType(), Integer.class);

        int[] ints = new int[0];
        ConstructorArgumentValues.ValueHolder holder3 =
                new ConstructorArgumentValues.ValueHolder(ints);
        assertEquals(holder3.getValue(), ints);
        assertEquals(holder3.getType(), ints.getClass());
    }
}