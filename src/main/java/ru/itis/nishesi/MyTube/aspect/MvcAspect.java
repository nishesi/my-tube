package ru.itis.nishesi.MyTube.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.itis.nishesi.MyTube.exceptions.ContentNotFoundException;


@Log4j2
@ControllerAdvice
public class MvcAspect {

    @InitBinder
    void init(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle(Model model, HttpServletRequest request) {
        log.debug("No mapping found for: " + request.getServletPath());

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
    public String handle(Exception ex) {
        if (log.isWarnEnabled())
            log.warn("Exception: " + ex.toString() + " from: " + ex.getStackTrace()[0].toString() + " mapped to view: errors/500");
        return "errors/500";
    }
}
