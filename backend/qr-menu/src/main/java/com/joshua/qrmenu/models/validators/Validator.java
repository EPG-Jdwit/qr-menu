package com.joshua.qrmenu.models.validators;

import com.joshua.qrmenu.endpoints.exceptions.InputException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Validates specified requirements of input data objects.
 */
public class Validator {

    /**
     * Applies an annotated validation to an input field.
     *
     * @param obj : The object with its fields.
     * @param mode : Specifies when the validation must happen, ie: when an object is created, when it's updated.
     * @throws InputException : If a validation fails.
     */
    public void validate(Object obj, ValidatorMode mode) throws InputException {
        Class<?> c = obj.getClass();
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType() == RequiredValidator.class) {
                        validateRequired(obj, field, mode);
                    }
                    if (annotation.annotationType() == OrderValidator.class) {
                        validateOrderValue(obj, field, mode);
                    }
                }
            }
            c = c.getSuperclass();
        }
    }

    /**
     * Validates that a required field has been provided.
     *
     * @param obj: The object for which the field has a required value.
     * @param field : The field that's required to have data.
     * @param mode : The mode for which it's required.
     * @throws InputException : If the field hasn't been provided while being required.
     */
    private void validateRequired(Object obj, Field field, ValidatorMode mode) throws InputException {
        field.setAccessible(true);
        RequiredValidator requiredValidator = field.getAnnotationsByType(RequiredValidator.class)[0];
        if (!Arrays.asList(requiredValidator.on()).contains(mode)) {
            return;
        }
        try {
            Object value = field.get(obj);
            if (value == null) {
                throw new InputException("Field '" + field.getName() + "' was required but not found.", field.getName());
            }
        } catch (IllegalAccessException e) {
            // TODO
        }
    }

    /**
     * Validates that the oder value is greater or equal to zero.
     *
     * @param obj : The object of which the field is to be checked.
     * @param field : The field of which the value must be greater or equal to zero.
     * @param mode : The mode for which it's to be checked.
     * @throws InputException : If the value of the field is lesser than zero.
     */
    private void validateOrderValue(Object obj, Field field, ValidatorMode mode) throws InputException {
        field.setAccessible(true);
        OrderValidator orderValidator = field.getAnnotationsByType(OrderValidator.class)[0];
        if (!Arrays.asList(orderValidator.on()).contains(mode)) {
            return;
        }
        try {
            int value = (Integer) field.get(obj);
            if (value < 0) {
                throw new InputException("Field '" + field.getName() + "'must be greater or equal to zero", field.getName());
            }
        } catch (IllegalAccessException e) {
            // TODO
        }
    }
}
