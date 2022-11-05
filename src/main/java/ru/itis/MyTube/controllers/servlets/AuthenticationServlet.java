package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Attributes;
import ru.itis.MyTube.auxiliary.PassPerformer;
import ru.itis.MyTube.auxiliary.validators.AuthenticationValidator;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.forms.AuthenticationForm;
import ru.itis.MyTube.model.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebServlet("/authenticate")
public class AuthenticationServlet extends HttpServlet {
    private UserService userService;
    private AuthenticationValidator validator;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute(Attributes.USER_SERVICE.toString());
        validator =  new AuthenticationValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("form", new AuthenticationForm());

        req.getRequestDispatcher("WEB-INF/jsp/AuthenticationPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AuthenticationForm form = AuthenticationForm.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .build();
        req.setAttribute("form", form);

        Map<String, String> problems = validator.validate(form);

        if (problems.isEmpty()) {
            Optional<User> userOptional = userService.get(form.getUsername(), PassPerformer.hash(form.getPassword()));

            if (userOptional.isPresent()) {

                req.getSession().setAttribute(Attributes.USER.toString(), userOptional.get());

                String url = (String) req.getSession().getAttribute("requestUrl");
                if (url != null) {
                    req.getSession().removeAttribute("requestUrl");
                    resp.sendRedirect(url);

                } else {
                    resp.sendRedirect(getServletContext().getContextPath());
                }
                return;
            }
            req.setAttribute("error", "User not found.");

        } else {
            req.setAttribute("problems", problems);

        }
        req.getRequestDispatcher("WEB-INF/jsp/AuthenticationPage.jsp").forward(req, resp);
    }

}
