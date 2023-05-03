package ru.itis.MyTube.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Repeatable(EqualFields.List.class)
@Constraint(validatedBy = {EqualFieldsValidator.class})
public @interface EqualFields {

    /**
     * Field names that should be equal.
     * @return field names
     */
    String[] value();

    String message() default "Fields not equals.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        EqualFields[] value();
    }
}
