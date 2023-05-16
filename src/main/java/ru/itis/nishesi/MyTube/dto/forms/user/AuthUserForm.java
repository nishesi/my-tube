package ru.itis.nishesi.MyTube.dto.forms.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthUserForm {
    private String username;
    private String password;
}
