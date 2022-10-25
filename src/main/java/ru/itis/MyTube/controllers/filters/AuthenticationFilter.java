package ru.itis.MyTube.controllers.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/private/*")
public class AuthenticationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String sessUsername = (String) req.getSession().getAttribute("username");

        if (sessUsername != null) {

            chain.doFilter(req, res);
        } else {
             res.sendRedirect(req.getContextPath() + "/authenticate");
        }

    }
}
