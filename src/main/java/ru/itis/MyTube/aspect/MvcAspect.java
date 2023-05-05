package ru.itis.MyTube.aspect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.view.Alert;

import java.util.Map;
import java.util.Queue;

@ControllerAdvice
public class MvcAspect {
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @InitBinder
    void init(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(ValidationException ex) {
        return ex.getProblems();
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handle(ServiceException ex, @SessionAttribute Queue<Alert> alerts) {

        alerts.add(Alert.of(Alert.AlertType.WARNING, ex.getMessage()));
        return ResponseEntity.status(302).header("Location", contextPath).build();
    }
}
