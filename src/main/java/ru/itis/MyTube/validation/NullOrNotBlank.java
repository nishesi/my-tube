package ru.itis.MyTube.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.SOURCE)
public @interface NullOrNotBlank {
    String message() default "{jakarta.validation.constraints.NotBlank.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
