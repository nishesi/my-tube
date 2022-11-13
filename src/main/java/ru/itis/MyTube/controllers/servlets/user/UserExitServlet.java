package ru.itis.MyTube.controllers.servlets.user;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.itis.MyTube.auxiliary.constants.Attributes.USER;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.PRIVATE_USER_EXIT;

@WebServlet(PRIVATE_USER_EXIT)
public class UserExitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute(USER);
        resp.sendRedirect(getServletContext().getContextPath());
    }
}
