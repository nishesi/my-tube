package ru.itis.nishesi.MyTube.aspect;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.itis.nishesi.MyTube.dto.rest.ExceptionDto;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;

@Order(1)
@RestControllerAdvice("ru.itis.nishesi.MyTube.restcontrollers")
public class RestAspect {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException ex) {
        return ResponseEntity.internalServerError()
                .body(new ExceptionDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(new ExceptionDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException() {
        return ResponseEntity.badRequest()
                .body(new ExceptionDto("No mapping found.", HttpStatus.BAD_REQUEST.toString()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ExceptionDto("Method not supported.", HttpStatus.METHOD_NOT_ALLOWED.toString()));
    }
}
