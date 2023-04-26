package ru.itis.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.dto.User;
import ru.itis.MyTube.dto.forms.RegistrationForm;
import ru.itis.MyTube.dto.forms.SubscribeForm;
import ru.itis.MyTube.dto.forms.UserUpdateForm;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.view.Alert;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        return "registrationPage";
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(
            @RequestBody RegistrationForm registrationForm,
            @SessionAttribute Queue<Alert> alerts) throws ValidationException {

        userService.save(registrationForm);
        alerts.add(Alert.of(Alert.AlertType.SUCCESS, "You registered."));

        return ResponseEntity
                .status(302)
                .header("Location", contextPath + "/login").build();
    }

    @PostMapping(value="/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String register(
            RegistrationForm registrationForm,
            RedirectAttributes redirectAttributes,
            ModelMap modelMap
    ) {
        try {
            userService.save(registrationForm);
            List<Alert> alerts = new ArrayList<>();
            alerts.add(Alert.of(Alert.AlertType.SUCCESS, "You registered."));
            redirectAttributes.addFlashAttribute("alerts", alerts);

            return "redirect:/login";
        } catch (ValidationException e) {
            modelMap.put("problems", e.getProblems());
            return "registrationPage";
        }
    }


    @GetMapping("/user/update")
    public String getUserUpdatePage() {
        return "userPage";
    }

    @PostMapping("/user")
    public String updateUser(HttpServletRequest req,
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

            return "redirect:" + contextPath + PRIVATE_USER_EXIT;
        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());

        } catch (ServiceException e) {
            alerts.add(new Alert(Alert.AlertType.DANGER, e.getMessage()));
        }
        return "userPage";
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
