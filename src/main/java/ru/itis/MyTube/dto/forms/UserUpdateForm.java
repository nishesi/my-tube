package ru.itis.MyTube.dto.forms;

import lombok.Builder;
import lombok.Data;

import jakarta.servlet.http.Part;

@Data
@Builder
public class UserUpdateForm extends AbstractForm{
    private Part iconPart;
    private String password;
    private String firstName;
    private String lastName;
    private String birthdate;
    private String country;
}
