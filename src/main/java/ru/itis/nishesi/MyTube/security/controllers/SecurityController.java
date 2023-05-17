package ru.itis.nishesi.MyTube.security.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.itis.nishesi.MyTube.dto.forms.user.AuthUserForm;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "user/auth";
    }

    @PostMapping("/auth/err")
    public String handle(@ModelAttribute AuthUserForm authUserForm, BindingResult bindingResult) {
        bindingResult.addError(new ObjectError("authUserForm", "User not found."));
        return "/user/auth";
    }

    @RequestMapping("/oauth/err")
    public String handle(@RequestAttribute(required = false) String message,
                         @ModelAttribute AuthUserForm authUserForm,
                         BindingResult bindingResult) {
        message = message == null ? "Service unavailable." : message;
        bindingResult.addError(new ObjectError("authUserForm", message));
        return "/user/auth";
    }

    @RequestMapping("/authorize/err")
    public String handleAuthorizeError() {
        return "errors/403";
    }
}
