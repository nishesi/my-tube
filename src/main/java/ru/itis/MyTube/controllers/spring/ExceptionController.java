package ru.itis.MyTube.controllers.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.view.Alert;

import java.util.Map;
import java.util.Queue;

@ControllerAdvice
public class ExceptionController {
    @Value("${context.path}")
    private String contextPath;

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Map<String, String> handle(ValidationException ex) {
        return ex.getProblems();
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handle(ServiceException ex, @SessionAttribute Queue<Alert> alerts) {

        alerts.add(Alert.of(Alert.AlertType.WARNING, ex.getMessage()));
        return ResponseEntity.status(302).header("Location", contextPath).build();
    }
}
