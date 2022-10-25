package ru.itis.MyTube.model.forms;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistrationForm {
    private String login;
    private String password;
    private String passwordRepeat;
    private String firstName;
    private String lastName;
    private String birthdate;
    private String sex;
    private String country;
    private String agreement;
}
