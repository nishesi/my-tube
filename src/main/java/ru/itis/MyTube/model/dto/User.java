package ru.itis.MyTube.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String sex;
    private String country;
}
