package ru.itis.nishesi.MyTube.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.nishesi.MyTube.dto.AlertsDto;
import ru.itis.nishesi.MyTube.dto.forms.user.NewUserForm;
import ru.itis.nishesi.MyTube.dto.forms.user.UpdateUserForm;
import ru.itis.nishesi.MyTube.exceptions.ExistsException;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.services.UserService;
import ru.itis.nishesi.MyTube.dto.Alert;
import ru.itis.nishesi.MyTube.enums.AlertType;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "user/new";
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
                        Alert.of(AlertType.SUCCESS, "You registered."));
                redirectAttributes.addFlashAttribute("alerts", alertsDto);

                return "redirect:/login";
            } catch (ExistsException ex) {
                bindingResult.addError(new FieldError("newUserForm", "email", ex.getMessage()));
            }
        }
        return "user/new";
    }


    @GetMapping("/update")
    public String getUpdateUserPage() {
        return "user/update";
    }

    @PutMapping
    public String updateUser(@Valid UpdateUserForm updateUserForm,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @SessionAttribute UserDto user
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                updateUserForm.setId(user.getId());
                userService.update(updateUserForm);

                AlertsDto alerts = new AlertsDto(
                        new Alert(AlertType.SUCCESS, "Your account information updated."),
                        new Alert(AlertType.INFO, "Please do reauthorization.")
                );
                redirectAttributes.addFlashAttribute("alerts", alerts);
                return "redirect:/logout";

            } catch (ServiceException e) {
                AlertsDto alerts = new AlertsDto(new Alert(AlertType.DANGER, e.getMessage()));
                redirectAttributes.addFlashAttribute("alerts", alerts);
            }
        }
        return "user/update";
    }

    @PostMapping("/subscribe")
    public String subscribeToChannel(@RequestParam Long channelId,
                                     @SessionAttribute UserDto user,
                                     RedirectAttributes redirectAttributes) {
        try {
            userService.changeSubscription(channelId, user.getId());
            return "redirect:/channel/" + channelId;
        } catch (ServiceException ex) {
            AlertsDto alertsDto = new AlertsDto(new Alert(AlertType.DANGER, ex.getMessage()));
            redirectAttributes.addFlashAttribute("alerts", alertsDto);
        }
        return "redirect:/";
    }
}
