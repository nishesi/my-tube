package ru.itis.MyTube.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.MyTube.auxiliary.exceptions.ExistsException;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.forms.NewUserForm;
import ru.itis.MyTube.dto.forms.SubscribeForm;
import ru.itis.MyTube.dto.forms.UpdateUserForm;
import ru.itis.MyTube.model.User;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.view.Alert;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "registrationPage";
    }

    @PostMapping
    public String register(@Valid NewUserForm newUserForm,
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
    public String getUpdateUserPage() {
        return "updateUserPage";
    }

    @PutMapping
    public String updateUser(@Valid UpdateUserForm updateUserForm,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @SessionAttribute User user
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                userService.update(updateUserForm, user);
                AlertsDto alerts = new AlertsDto(
                        new Alert(Alert.AlertType.SUCCESS, "Your account information updated."),
                        new Alert(Alert.AlertType.INFO, "Please do reauthorization.")
                );
                redirectAttributes.addFlashAttribute("alerts", alerts);

                return "redirect:/logout";
            } catch (ServiceException e) {
                AlertsDto alerts = new AlertsDto(new Alert(Alert.AlertType.DANGER, e.getMessage()));
                redirectAttributes.addFlashAttribute("alerts", alerts);
            }
        }
        return "updateUserPage";
    }

    @PostMapping("/subscribe")
    public String subscribeToChannel(SubscribeForm subscribeForm,
                                     @SessionAttribute User user,
                                     RedirectAttributes redirectAttributes) {
        try {
            subscribeForm.setUser(user);
            userService.userChannel(subscribeForm);
        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
            redirectAttributes.addFlashAttribute("alerts", alertsDto);
        }
        redirectAttributes.addAttribute("id", subscribeForm.getChannelId());
        return "redirect:/channel";
    }
}
