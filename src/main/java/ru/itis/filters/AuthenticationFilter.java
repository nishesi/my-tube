package ru.itis.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@WebFilter("/private/*")
public class AuthenticationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String sessUsername = (String) req.getSession().getAttribute("username");
        String cookieUsername = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("username"))
                .findFirst().orElse(new Cookie("a", null)).getValue();

        if (sessUsername != null && sessUsername.equals(cookieUsername)) {

            chain.doFilter(req, res);
        } else {
            req.getRequestDispatcher("/authenticate").forward(req, res);
        }

    }
}
