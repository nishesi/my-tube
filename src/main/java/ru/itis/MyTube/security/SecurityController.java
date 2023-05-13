package ru.itis.MyTube.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.MyTube.dto.forms.user.AuthUserForm;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "user/auth";
    }

    @PostMapping("/login/err")
    public String handle(@ModelAttribute AuthUserForm authUserForm, BindingResult bindingResult) {
        bindingResult.addError(new ObjectError("authUserForm", "User not found."));
        return "/user/auth";
    }

    @RequestMapping("/auth/err/rest")
    public ResponseEntity<?> handle() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("""
                {
                    "message" : "You not authenticated."
                }
                """);
    }
}
