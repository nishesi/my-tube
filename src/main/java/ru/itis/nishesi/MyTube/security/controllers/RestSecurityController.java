package ru.itis.nishesi.MyTube.security.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestSecurityController {

    @RequestMapping("/authorize/err")
    public ResponseEntity<?> handleRestAuthorizeError() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {
                            "message" : "You not have permission. Try reload page."
                        }
                        """);
    }

    @RequestMapping("/auth/err")
    public ResponseEntity<?> handle() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {
                            "message" : "You not authenticated."
                        }
                        """);
    }
}
