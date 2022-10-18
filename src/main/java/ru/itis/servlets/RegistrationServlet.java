package ru.itis.servlets;

import ru.itis.auxilary.PassPerformer;
import ru.itis.db.UserRepJdbcImpl;
import ru.itis.db.UserRepository;
import ru.itis.dto.User;
import ru.itis.forms.RegistrationForm;
import ru.itis.validator.RegistrationValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    private final RegistrationValidator registrationValidator = new RegistrationValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        initPage(req);
        req.setAttribute("form", new RegistrationForm());
        try {
            req.getRequestDispatcher("/WEB-INF/register-page.jsp").forward(req, resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        try (
//                InputStream in = getServletContext().getResourceAsStream("/reg-page/reg-page.html")
//        ) {
//
//            resp.getOutputStream().write(in.readAllBytes());
//
//        } catch (IOException ex) {
//            throw new ServletException(ex);
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        initPage(req);

        RegistrationForm registrationForm = new RegistrationForm(
                req.getParameter("login"),
                req.getParameter("password"),
                req.getParameter("passwordRepeat"),
                req.getParameter("firstName"),
                req.getParameter("lastName"),
                req.getParameter("birthdate"),
                req.getParameter("sex"),
                req.getParameter("country"),
                req.getParameter("agreement")
        );
        
        Map<String, String> problems = registrationValidator.validate(registrationForm, getUserRepository());
        problems.forEach(req::setAttribute);

        req.setAttribute("form", registrationForm);
        try {
            req.getRequestDispatcher("/WEB-INF/register-page.jsp").forward(req, resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        try (BufferedReader in = new BufferedReader(
//                new InputStreamReader(
//                        getServletContext().getResourceAsStream("/reg-page/reg-page.html")
//                )
//        )) {
//
//            if (problems.isEmpty()) {
//
//                saveUser(registrationForm, resp);
//
//            } else {
//
//                String page = in.lines().collect(Collectors.joining());
//
//                String errors = String.join("<br>", problems.values());
//
//                resp.getWriter().println(page.replace("errors", errors));
//
//            }
//
//        } catch (IOException ex) {
//            throw new ServletException(ex);
//        }
    }

    protected void saveUser(RegistrationForm form, HttpServletResponse resp) throws IOException{
        User newUser = new User(
                form.getLogin(),
                PassPerformer.hash(form.getPassword()),
                form.getFirstName(),
                form.getLastName(),
                LocalDate.parse(form.getBirthDate()),
                form.getSex(),
                form.getCountry()
        );

        UserRepository repository = getUserRepository();
        if (repository.save(newUser)) {
            resp.setStatus(200);
            resp.getWriter().println("you registered");
        } else {
            resp.sendError(500, "registration failed");
        }
    }


    protected UserRepository getUserRepository() {
        return UserRepJdbcImpl.getRepository();
    }

    private void initPage(HttpServletRequest req) {
        req.setAttribute("regPageCss", req.getContextPath() + "/css/reg-page.css");
    }
}
