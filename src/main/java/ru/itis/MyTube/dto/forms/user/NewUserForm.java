package ru.itis.MyTube.dto.forms.user;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.MyTube.validation.EqualFields;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualFields(value = {"password", "passwordRepeat"}, message = "Passwords not equals")
public class NewUserForm {

    @NotBlank
    @Size(max = 255)
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 60)
    private String password;

    private String passwordRepeat;

    @NotBlank
    @Size(max=15)
    private String firstName;

    @NotBlank
    @Size(max=15)
    private String lastName;

    @NotNull
    @Past
    private LocalDate birthdate;

    @NotBlank
    @Size(max = 20)
    private String country;

    @NotNull(message = "You should agree with agreement.")
    private String agreement;
}




