package com.joshua.qrmenu.models.validators;

import com.joshua.qrmenu.endpoints.exceptions.InputException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

public class Validator {

    public void validate(Object obj, ValidatorMode mode) throws InputException {
        Class<?> c = obj.getClass();
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType() == RequiredValidator.class) {
                        validateRequired(obj, field, mode);
                    }
                }
            }
            c = c.getSuperclass();
        }
    }

    private <T extends Annotation> T getValidator(Field field, Class<T> annotationClass) {
        return field.getAnnotationsByType(annotationClass)[0];
    }

    private void validateRequired(Object obj, Field field, ValidatorMode mode) throws InputException {
        field.setAccessible(true);
        RequiredValidator requiredValidator = getValidator(field, RequiredValidator.class);
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
            return;
        }

    }
}
