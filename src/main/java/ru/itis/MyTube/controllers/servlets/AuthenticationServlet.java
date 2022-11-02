package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Attributes;
import ru.itis.MyTube.auxiliary.validators.AuthenticationValidator;
import ru.itis.MyTube.model.dao.interfaces.UserRepository;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.forms.AuthenticationForm;

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


    private final AuthenticationValidator validator = new AuthenticationValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        initPage(req);
        req.getRequestDispatcher("WEB-INF/jsp/authent-page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        initPage(req);

        AuthenticationForm form = AuthenticationForm.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .build();

        Map<String, String> problems = validator.validate(form);

        if (problems.isEmpty()) {
            Optional<User> userOptional = getUserRepository().get(form.getUsername());

            if (userOptional.isPresent()) {

                req.getSession().setAttribute(Attributes.USER.toString(), userOptional.get());

                String url = (String) req.getSession().getAttribute("requestUrl");
                req.getSession().removeAttribute("requestUrl");
                resp.sendRedirect(url);
                return;

            }
            req.setAttribute("error", "User not found.");

        } else {
            req.setAttribute("problems", problems);

        }
        req.getRequestDispatcher("WEB-INF/jsp/authent-page.jsp").forward(req, resp);
    }

    private void initPage(HttpServletRequest req) {
        req.setAttribute("regPageLink", getServletContext().getContextPath() + "/register");
        req.setAttribute("username", req.getParameter("username"));
        req.setAttribute("password", req.getParameter("password"));
    }

    protected UserRepository getUserRepository() {
        return (UserRepository) getServletContext().getAttribute(Attributes.USER_REP.toString());
    }
}
