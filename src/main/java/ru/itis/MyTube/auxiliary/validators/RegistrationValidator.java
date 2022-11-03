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

        validateLogin(form.getLogin(), problems);
        validatePassword(form.getPassword(), form.getPasswordRepeat(), problems);

        validateFirstName(form.getFirstName(), problems);
        validateLastName(form.getLastName(), problems);

        validateBirthDate(form.getBirthdate(), problems);
        validateSex(form.getSex(), problems);
        validateCountry(form.getCountry(), problems);

        validateAgreement(form.getAgreement(), problems);

        return problems;
    }

    protected void validateLogin(String login, Map<String, String> problems) {
        if (login == null || login.equals("")) {
            problems.put("login", "Login can not be empty.");

        } else if (!onlyLettersPat.matcher(login).matches()) {
            problems.put("login", "Login should have only letters.");

        } else if (userService.get(login).isPresent()) {
            problems.put("login", "This login is exist.");
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

    protected void validateSex(String sex, Map<String, String> problems) {
        if (sex == null || sex.equals("")) {
            problems.put("sex", "Your sex is not chosen.");
        } else if (!sex.equals("male") && !sex.equals("female") && !sex.equals("another")) {
            problems.put("sex", "Unknown statement of sex.");
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
