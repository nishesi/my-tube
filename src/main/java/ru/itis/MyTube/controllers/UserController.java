package ru.itis.MyTube.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.MyTube.dto.AlertsDto;
import ru.itis.MyTube.dto.forms.user.NewUserForm;
import ru.itis.MyTube.dto.forms.user.UpdateUserForm;
import ru.itis.MyTube.exceptions.ExistsException;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.services.UserService;
import ru.itis.MyTube.view.Alert;

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
                        Alert.of(Alert.AlertType.SUCCESS, "You registered."));
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
                             @SessionAttribute UserDto userDto
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                updateUserForm.setId(userDto.getId());
                userService.update(updateUserForm);

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
            AlertsDto alertsDto = new AlertsDto(new Alert(Alert.AlertType.DANGER, ex.getMessage()));
            redirectAttributes.addFlashAttribute("alerts", alertsDto);
        }
        return "redirect:/";
    }
}
