package ru.itis.nishesi.MyTube.validation.rest.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itis.nishesi.MyTube.validation.rest.responses.ValidationErrorDto;
import ru.itis.nishesi.MyTube.validation.rest.responses.ValidationErrorsDto;

import java.util.List;
import java.util.Locale;

@RestControllerAdvice(basePackages = "ru.itis.nishesi.MyTube.restcontrollers")
@RequiredArgsConstructor
@Order(0)
public class ValidationExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationErrorsDto handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, Locale locale
    ) {
        List<ValidationErrorDto> errors = ex.getAllErrors().stream()
                .map(objectError -> {
                            var vedBuilder = ValidationErrorDto.builder();

                            if (objectError instanceof FieldError fieldError) {
                                vedBuilder.field(fieldError.getField());
                                vedBuilder.value(fieldError.getRejectedValue());
                            }

                            vedBuilder
                                    .object(objectError.getObjectName())
                                    .message(messageSource.getMessage(objectError, locale));
                            return vedBuilder.build();
                        }

                ).toList();

        return new ValidationErrorsDto(errors);
    }

}
