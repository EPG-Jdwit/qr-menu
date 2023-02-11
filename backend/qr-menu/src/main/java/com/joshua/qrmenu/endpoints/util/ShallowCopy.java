package com.joshua.qrmenu.endpoints.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to shallow copy fields from one Entity to another
 */
public class ShallowCopy {

    // Private constructor for static class
    private ShallowCopy() {
    }

    /**
     * Method to copy fields of two Objects of the same type. Fields that weren't given do not get copied.
     *
     * @param from : Source Object.
     * @param to : Target Object.
     */
    public static void copyFieldsExceptNull(Object from, Object to) {
        if (from.getClass() != to.getClass()) {
            throw new RuntimeException("Can only copy matching classes");
        }

        List<Field> fields = new ArrayList<>();
        Class c = from.getClass();
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers)) {
                    fields.add(field);
                }
            }
            c = c.getSuperclass();

            try {
                for (Field sourceField : fields) {
                    Object sourceValue = sourceField.get(from);
                    if (sourceValue != null) {
                        sourceField.set(to, sourceValue);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException();
            }
        }
    }
}
