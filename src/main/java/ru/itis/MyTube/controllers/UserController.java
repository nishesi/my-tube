package ru.itis.MyTube.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.MyTube.auxiliary.exceptions.ExistsException;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.forms.NewUserForm;
import ru.itis.MyTube.dto.forms.SubscribeForm;
import ru.itis.MyTube.dto.forms.UserUpdateForm;
import ru.itis.MyTube.model.User;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.view.Alert;

import java.io.IOException;
import java.util.Queue;

import static ru.itis.MyTube.controllers.UrlPatterns.CHANNEL;
import static ru.itis.MyTube.controllers.UrlPatterns.PRIVATE_USER_EXIT;
import static ru.itis.MyTube.view.Attributes.USER;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @Value("${context.path}")
    private String contextPath;

    @GetMapping("/register")
    public String getRegisterPage() {
        return "registrationPage";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(
            @RequestBody NewUserForm newUserForm,
            @SessionAttribute Queue<Alert> alerts) throws ValidationException {

        userService.save(newUserForm);
        alerts.add(Alert.of(Alert.AlertType.SUCCESS, "You registered."));

        return ResponseEntity
                .status(302)
                .header("Location", contextPath + "/login").build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String register(
            @Valid NewUserForm newUserForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                userService.save(newUserForm);
                AlertsDto alertsDto = new AlertsDto(
                        Alert.of(Alert.AlertType.SUCCESS, "You registered."));
                redirectAttributes.addFlashAttribute("alerts", alertsDto);

                return "redirect:/login";
            } catch (ExistsException ex) {
                bindingResult.addError(new ObjectError("newUserForm", ex.getMessage()));
            }
        }
        return "registrationPage";
    }


    @GetMapping("/update")
    public String getUserUpdatePage() {
        return "userPage";
    }

    @PutMapping
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
        } catch (ServiceException e) {
            alerts.add(new Alert(Alert.AlertType.DANGER, e.getMessage()));
        }
        return "userPage";
    }

    @PostMapping("/subscribe")
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
