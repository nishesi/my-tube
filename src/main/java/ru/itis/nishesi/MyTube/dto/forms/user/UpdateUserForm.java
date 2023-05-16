package ru.itis.nishesi.MyTube.dto.forms.user;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.nishesi.MyTube.validation.constraints.EqualFields;
import ru.itis.nishesi.MyTube.validation.constraints.FileSize;
import ru.itis.nishesi.MyTube.validation.constraints.NullOrNotBlank;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualFields(value = {"password", "passwordRepeat"}, message = "Passwords not equals")
public class UpdateUserForm {
    private Long id;

    @FileSize(max = 10*1024*1024*8)
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



