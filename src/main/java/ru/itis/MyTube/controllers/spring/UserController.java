package ru.itis.MyTube.controllers.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.RegistrationForm;
import ru.itis.MyTube.model.dto.forms.SubscribeForm;
import ru.itis.MyTube.model.dto.forms.UserUpdateForm;
import ru.itis.MyTube.model.services.UserService;
import ru.itis.MyTube.view.Alert;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;

import static ru.itis.MyTube.controllers.UrlPatterns.CHANNEL;
import static ru.itis.MyTube.controllers.UrlPatterns.PRIVATE_USER_EXIT;
import static ru.itis.MyTube.view.Attributes.USER;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Value("${context.path}")
    private String contextPath;

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("regPageCss", contextPath + "/static/css/reg-page.css");
        return "/jsp/RegistrationPage";
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<?> register(
            @RequestBody RegistrationForm registrationForm,
            @SessionAttribute Queue<Alert> alerts) throws ValidationException {

        userService.save(registrationForm);
        alerts.add(Alert.of(Alert.AlertType.SUCCESS, "You registered."));

        return ResponseEntity
                .status(302)
                .header("Location", contextPath + "/login").build();
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
            userService.update(form, (User) req.getSession().getAttribute("user"));

            alerts.add(new Alert(Alert.AlertType.SUCCESS, "Your account information updated."));
            alerts.add(new Alert(Alert.AlertType.INFO, "Please do reauthorization."));

            resp.sendRedirect(contextPath + PRIVATE_USER_EXIT);
            return;
        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());

        } catch (ServiceException e) {
            alerts.add(new Alert(Alert.AlertType.DANGER, e.getMessage()));
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
            alerts.add(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
        }
        resp.sendRedirect(contextPath + CHANNEL + "?id=" + subscribeForm.getChannelId());
    }
}
