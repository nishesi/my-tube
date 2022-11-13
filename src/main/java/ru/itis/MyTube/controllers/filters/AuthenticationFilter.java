package ru.itis.MyTube.controllers.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.itis.MyTube.auxiliary.constants.Attributes.USER;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.*;


@WebFilter(PRIVATE + "/*")
public class AuthenticationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String url = req.getRequestURI();

        if (req.getSession().getAttribute(USER) != null) {

            chain.doFilter(req, res);
        } else {
            req.getSession().setAttribute("requestUrl", url);
            res.sendRedirect(getServletContext().getContextPath() + AUTHENTICATION_PAGE);
        }

    }
}
