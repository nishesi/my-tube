package ru.itis.MyTube.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SecurityController {
    @Value("${context.path}")
    private String contextPath;
    private final UserService userService;
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("regPageCss",  contextPath + "/static/css/reg-page.css");
        model.addAttribute("backImgUrl", contextPath + "/static/images/reg-background-img.jpg");
        return "/jsp/AuthenticationPage";
    }

    @PostMapping("/pr_lg")
    public ResponseEntity<?> processLogin(@RequestParam String username,
                                          @RequestParam String password,
                                          HttpSession session) {
        try {
            User user = userService.get(username, password);
            session.setAttribute("user", user);
            return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                    .header("Location",contextPath).build();

        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
