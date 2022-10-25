package ru.itis.MyTube.model.forms;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationForm {

    private String username;

    private String password;

}
