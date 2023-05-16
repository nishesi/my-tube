package ru.itis.nishesi.MyTube.validation.validators;

import jakarta.validation.*;
import ru.itis.nishesi.MyTube.validation.constraints.EqualFields;

import java.lang.reflect.Field;
import java.util.Objects;

public class EqualFieldsValidator implements ConstraintValidator<EqualFields, Object> {
    private String[] fieldNames;

    @Override
    public void initialize(EqualFields constraintAnnotation) {
        fieldNames = constraintAnnotation.value();
        if (fieldNames.length < 2)
            throw new ConstraintDeclarationException("nothing to validate");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            //should not happen
            throw new ValidationException("Class level validator get as input null value");
        }
        Object[] fieldValues = getRequiredFieldValues(value);

        for (int i = 0; i < fieldValues.length - 1; i++) {
            if (!Objects.equals(fieldValues[i], fieldValues[i+1])) {
                return false;
            }
        }
        return true;
    }

    private Object[] getRequiredFieldValues(Object value) {
        Object[] fieldValues = new Object[fieldNames.length];
        Class<?> cl = value.getClass();

        try {
            for (int i = 0; i < fieldNames.length; i++) {
                Field field = cl.getDeclaredField(fieldNames[i]);
                field.setAccessible(true);
                fieldValues[i] = field.get(value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ValidationException("evaluation exception", e);
        }
        return fieldValues;
    }
}
