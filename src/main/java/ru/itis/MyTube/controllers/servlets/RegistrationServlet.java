package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Attributes;
import ru.itis.MyTube.auxiliary.PassPerformer;
import ru.itis.MyTube.auxiliary.validators.RegistrationValidator;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.forms.RegistrationForm;
import ru.itis.MyTube.model.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private UserService userService;
    private RegistrationValidator registrationValidator;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute(Attributes.USER_SERVICE.toString());
        registrationValidator = new RegistrationValidator(userService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        initPage(req);
        req.setAttribute("form", RegistrationForm.builder().build());

        try {
            req.getRequestDispatcher("/WEB-INF/jsp/register-page.jsp").forward(req, resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        initPage(req);

        RegistrationForm registrationForm = RegistrationForm.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .passwordRepeat(req.getParameter("passwordRepeat"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .birthdate(req.getParameter("birthdate"))
                .sex(req.getParameter("sex"))
                .country(req.getParameter("country"))
                .agreement(req.getParameter("agreement"))
                .build();

        Map<String, String> problems = registrationValidator.validate(registrationForm);
        req.setAttribute("form", registrationForm);

        if (problems.isEmpty()) {
            saveUser(registrationForm);
            resp.sendRedirect(getServletContext().getContextPath() + "/authenticate");

        } else {
            req.setAttribute("problems", problems);

            try {
                req.getRequestDispatcher("/WEB-INF/jsp/register-page.jsp").forward(req, resp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void saveUser(RegistrationForm form) {
        User newUser = User.builder()
                .username(form.getLogin())
                .password(PassPerformer.hash(form.getPassword()))
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .birthdate(LocalDate.parse(form.getBirthdate()))
                .country(form.getCountry())
                .build();


        userService.save(newUser);
    }

    private void initPage(HttpServletRequest req) {
        req.setAttribute("regPageCss", req.getContextPath() + "/css/reg-page.css");
        req.setAttribute("problems", new HashMap<String, String>());
    }
}
