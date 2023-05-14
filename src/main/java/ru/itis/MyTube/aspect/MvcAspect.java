package ru.itis.MyTube.aspect;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.itis.MyTube.exceptions.ContentNotFoundException;

@ControllerAdvice
public class MvcAspect {

    @InitBinder
    void init(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle(Model model) {
        model.addAttribute("name", "page");
        return "errors/404";
    }

    @ExceptionHandler(ContentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle(ContentNotFoundException ex, Model model) {
        model.addAttribute("name", ex.getMessage());
        return "errors/404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException() {
        return "errors/500";
    }
}
