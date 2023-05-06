package ru.itis.MyTube.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SecurityController {
    private final UserService userService;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @GetMapping("/login")
    public String getLoginPage() {
        return "user/auth";
    }

    @PostMapping("/pr_lg")
    public ResponseEntity<?> processLogin(@RequestParam String username,
                                          @RequestParam String password,
                                          HttpSession session) {
//        UserDto userDto = userService.get(username, password);
//        session.setAttribute("user", userDto);
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .header("Location", contextPath).build();

    }
}
