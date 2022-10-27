package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxilary.Attributes;
import ru.itis.MyTube.auxilary.PassPerformer;
import ru.itis.MyTube.auxilary.validators.AuthenticationValidator;
import ru.itis.MyTube.model.dao.interfaces.UserRepository;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.forms.AuthenticationForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/authenticate")
public class AuthenticationServlet extends HttpServlet {


    private final AuthenticationValidator validator = new AuthenticationValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        initPage(req);
        req.getRequestDispatcher("WEB-INF/authent-page.jsp").forward(req, resp);
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

            if (isRegistered(form)) {

                req.getSession().setAttribute("username", form.getUsername());


                {
                    resp.getWriter().println("<html><body>" +
                            "you authorized." +
                            "<a href=''>home</a>" +
                            "</body></html>");

                    //TODO forward anywhere
                }

            } else {
                req.setAttribute("error", "User not found.");
                req.getRequestDispatcher("WEB-INF/authent-page.jsp").forward(req, resp);
            }

        } else {
            req.setAttribute("problems", problems);

            req.getRequestDispatcher("WEB-INF/authent-page.jsp").forward(req, resp);
        }
    }

    private boolean isRegistered(AuthenticationForm form) {
        List<User> userList = getUserRepository().getAll();

        return userList.stream()
                .anyMatch(user ->
                        user.getLogin().equals(form.getUsername()) &&
                                user.getPassword().equals(PassPerformer.hash(form.getPassword()))
                );
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
