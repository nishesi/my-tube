package ru.itis.MyTube.auxiliary.validators;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.forms.RegistrationForm;
import ru.itis.MyTube.model.services.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class RegistrationValidator {
    private final UserService userService;

    protected Pattern onlyLettersPat = Pattern.compile("[A-Za-zА-Яа-я]+");

    public Map<String, String> validate(RegistrationForm form) {
        Map<String, String> problems = new HashMap<>();

        validateUsername(form.getUsername(), problems);
        validatePassword(form.getPassword(), form.getPasswordRepeat(), problems);

        validateFirstName(form.getFirstName(), problems);
        validateLastName(form.getLastName(), problems);

        validateBirthDate(form.getBirthdate(), problems);
        validateCountry(form.getCountry(), problems);

        validateAgreement(form.getAgreement(), problems);

        return problems;
    }

    protected void validateUsername(String username, Map<String, String> problems) {
        if (username == null || username.equals("")) {
            problems.put("username", "Username can not be empty.");

        } else if (!onlyLettersPat.matcher(username).matches()) {
            problems.put("username", "Username should have only letters.");

        } else if (userService.usernameIsExist(username)) {
            problems.put("username", "This username is exist.");
        }
    }

    protected void validatePassword(String password, String passRepeat, Map<String, String> problems) {
        if (password == null || password.equals("")) {
            problems.put("password", "Password can not be empty.");
        } else if (!password.equals(passRepeat)) {
            problems.put("passwordRepeat", "Password and it's repeat is not equal.");
        }
    }

    protected void validateFirstName(String fName, Map<String, String> problems) {
        if (fName == null || fName.equals("")) {
            problems.put("firstName", "First name can not be empty.");
        } else if (!onlyLettersPat.matcher(fName).matches()) {
            problems.put("firstName", "First name should have only letters.");
        }
    }

    protected void validateLastName(String lName, Map<String, String> problems) {
        if (lName == null || lName.equals("")) {
            problems.put("lastName", "Last name can not be empty.");
        } else if (!onlyLettersPat.matcher(lName).matches()) {
            problems.put("lastName", "Last name should have only letters.");
        }
    }

    protected void validateBirthDate(String birthDate, Map<String, String> problems) {
        LocalDate bDate;
        try {
            bDate = LocalDate.parse(birthDate);

            if (bDate.isAfter(LocalDate.now()) || bDate.plusYears(100).isBefore(LocalDate.now())) {
                problems.put("birthdate", "Unreal value of birth date.");

            } else if (bDate.plusYears(10).isAfter(LocalDate.now())) {
                problems.put("error", "You are too young for this site.");
            }

        } catch (DateTimeParseException ex) {
            problems.put("birthdate", "Birth date can not be empty.");
        }
    }

    protected void validateCountry(String country, Map<String, String> problems) {
        if (country == null || country.equals("")) {
            problems.put("country", "Country can not be empty.");

        } else if (!onlyLettersPat.matcher(country).matches()) {
            problems.put("country", "Country should have only letters.");
        }
    }

    protected void validateAgreement(String agreement, Map<String, String> problems) {
        if (agreement == null || agreement.equals("")) {
            problems.put("agreement", "You should agree with agreement.");

        } else if (!agreement.equals("on")) {
            problems.put("error", "something go wrong");
        }
    }

}
