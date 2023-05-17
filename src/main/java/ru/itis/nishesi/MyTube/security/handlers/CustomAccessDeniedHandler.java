package ru.itis.nishesi.MyTube.security.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Value("${mvc.rest-path}")
    private String restPath;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String path = request.getServletPath().startsWith(restPath) ?
                restPath + "/authorize/err" :
                "/authorize/err";
        request.getRequestDispatcher(path).forward(request, response);
    }
}
