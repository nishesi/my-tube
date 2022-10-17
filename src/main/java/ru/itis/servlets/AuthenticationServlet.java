package ru.itis.servlets;

import ru.itis.auxilary.PassPerformer;
import ru.itis.db.UserRepJdbcImpl;
import ru.itis.db.UserRepository;
import ru.itis.dto.User;
import ru.itis.forms.AuthenticationForm;
import ru.itis.validator.AuthenticationValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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

        AuthenticationForm form = new AuthenticationForm(
                req.getParameter("username"),
                req.getParameter("password")
        );

        Map<String, String> problems = validator.validate(form);

        if (problems.isEmpty()) {

            if (isRegistered(form)) {

                req.getSession().setAttribute("username", form.getUsername());
                resp.addCookie(new Cookie("username", form.getUsername()));

                resp.getWriter().println("<html><body>" +
                        "you authorized." +
                        "<a href=''>home</a>" +
                        "</body></html>");

                //TODO forward anywhere

            } else {
                req.setAttribute("error", "User not found.");
                req.getRequestDispatcher("WEB-INF/authent-page.jsp").forward(req, resp);
            }

        } else {
            problems.forEach(req::setAttribute);

            req.getRequestDispatcher("WEB-INF/authent-page.jsp").forward(req, resp);
        }
    }

    private boolean isRegistered(AuthenticationForm form) {
        List<User> userList = getUserRepository().getAll();

        return getUserRepository().getAll().stream()
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
        return UserRepJdbcImpl.getRepository();
    }
}
