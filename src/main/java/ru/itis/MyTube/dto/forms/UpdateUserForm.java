package ru.itis.MyTube.dto.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.MyTube.validation.EqualFields;
import ru.itis.MyTube.validation.NullOrNotBlank;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualFields(value = {"password", "passwordRepeat"}, message = "Passwords not equals")
public class UpdateUserForm {
    private MultipartFile iconFile;

    @NullOrNotBlank
    @Size(min = 4, max = 60)
    private String password;

    private String passwordRepeat;

    @NullOrNotBlank
    @Size(max=15)
    private String firstName;

    @NullOrNotBlank
    @Size(max=15)
    private String lastName;

    @Past
    private LocalDate birthdate;

    @NullOrNotBlank
    @Size(max = 20)
    private String country;
}



