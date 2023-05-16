package ru.itis.nishesi.MyTube.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.itis.nishesi.MyTube.validation.validators.NullOrNotBlankValidator;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
@Target( { FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NullOrNotBlank {
    String message() default "{jakarta.validation.constraints.NotBlank.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
