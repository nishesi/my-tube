package ru.itis.nishesi.MyTube.security.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.nishesi.MyTube.dto.rest.ExceptionDto;

@RestController
@RequestMapping("/api")
public class RestSecurityController {

    @Hidden
    @RequestMapping("/authorize/err")
    public ResponseEntity<?> handleRestAuthorizeError() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ExceptionDto(
                        "You not have permission. Try reload page.",
                        HttpStatus.FORBIDDEN.toString()));
    }

    @Hidden
    @RequestMapping("/auth/err")
    public ResponseEntity<?> handle() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ExceptionDto(
                        "You not authenticated.",
                        HttpStatus.FORBIDDEN.toString()));
    }
}
