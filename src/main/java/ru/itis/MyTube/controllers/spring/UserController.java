package ru.itis.MyTube.controllers.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.RegistrationForm;
import ru.itis.MyTube.model.dto.forms.SubscribeForm;
import ru.itis.MyTube.model.dto.forms.UserUpdateForm;
import ru.itis.MyTube.model.services.UserService;
import ru.itis.MyTube.view.Alert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;

import static ru.itis.MyTube.controllers.UrlPatterns.*;
import static ru.itis.MyTube.view.Attributes.USER;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Value("${context.path}")
    private String contextPath;

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "/jsp/RegistrationPage";
    }

    @PostMapping("/register")
    public void register(HttpServletRequest req,
                         HttpServletResponse resp,
                         @SessionAttribute Queue<? super Alert> alerts
    ) throws IOException, ServletException {

        RegistrationForm registrationForm = RegistrationForm.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .passwordRepeat(req.getParameter("passwordRepeat"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .birthdate(req.getParameter("birthdate"))
                .country(req.getParameter("country"))
                .agreement(req.getParameter("agreement"))
                .build();

        req.setAttribute("form", registrationForm);
        try {
            userService.save(registrationForm);
            alerts.add(new Alert(Alert.alertType.SUCCESS, "You registered."));
            resp.sendRedirect(contextPath + AUTHENTICATION_PAGE);

        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());
            req.getRequestDispatcher("/WEB-INF/jsp/RegistrationPage.jsp").forward(req, resp);

        } catch (ServiceException e) {
            alerts.add(new Alert(Alert.alertType.DANGER, e.getMessage()));
            resp.sendRedirect(contextPath);
        }
    }

    @GetMapping("/user/update")
    public String getUserUpdatePage() {
        return "/jsp/UserPage";
    }

    @PostMapping("/user")
    public void updateUser(HttpServletRequest req,
                           HttpServletResponse resp,
                           @SessionAttribute Queue<? super Alert> alerts
    ) throws ServletException, IOException {

        UserUpdateForm form = UserUpdateForm.builder()
                .iconPart(req.getPart("icon"))
                .password(req.getParameter("password"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .birthdate(req.getParameter("birthdate"))
                .country(req.getParameter("country")).build();

        try {
            userService.update(form,(User)req.getSession().getAttribute("user"));

            alerts.add(new Alert(Alert.alertType.SUCCESS, "Your account information updated."));
            alerts.add(new Alert(Alert.alertType.INFO, "Please do reauthorization."));

            resp.sendRedirect(contextPath + PRIVATE_USER_EXIT);
            return;
        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());

        } catch (ServiceException e) {
            alerts.add(new Alert(Alert.alertType.DANGER, e.getMessage()));
        }
        req.getRequestDispatcher("/WEB-INF/jsp/UserPage.jsp").forward(req, resp);
    }

    @PostMapping
    public void subscribeToChannel(HttpServletRequest req,
                                   HttpServletResponse resp,
                                   @SessionAttribute Queue<? super Alert> alerts) throws IOException {
        SubscribeForm subscribeForm = SubscribeForm.builder()
                .user((User) req.getSession().getAttribute(USER))
                .toSubscribe(req.getParameter("type"))
                .channelId(req.getParameter("channelId"))
                .build();

        try {
            userService.userChannel(subscribeForm);
        } catch (ServiceException ex) {
            alerts.add(new Alert(Alert.alertType.DANGER, ex.getMessage()));
        }
        resp.sendRedirect(contextPath + CHANNEL + "?id=" + subscribeForm.getChannelId());
    }
}
