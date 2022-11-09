package ru.itis.MyTube.auxiliary.validators;

import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.forms.UserUpdateForm;

import javax.servlet.http.Part;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UserUpdateValidator {

    protected Pattern onlyLettersPat = Pattern.compile("[A-Za-zА-Яа-я]+");

    public void validate(UserUpdateForm form) throws ValidationException {
        Map<String, String> problems = new HashMap<>();
        validatePart(form.getIconPart(), problems);
        validatePassword(form.getPassword(), problems);
        validateFirstName(form.getFirstName(), problems);
        validateLastName(form.getLastName(), problems);
        validateBirthDate(form.getBirthdate(), problems);
        validateCountry(form.getCountry(), problems);

        if (!problems.isEmpty()) {
            throw new ValidationException(problems);
        }
    }

    private void validatePart(Part iconPart, Map<String, String> problems) {
        if (!"image/jpg".equals(iconPart.getContentType())) {
            problems.put("icon", "File type should be jpg.");
        } else if (!iconPart.getSubmittedFileName().endsWith(".jpg")) {
            problems.put("icon", "File type should be jpg. (2)");
        } else if (iconPart.getSize() <= 100) {
            problems.put("icon", "Image size very small.");
        } else if (iconPart.getSize() >= 5 * 1024 * 1024) {
            problems.put("icon", "File size very big.");
        }
    }

    private void validatePassword(String password, Map<String, String> problems) {
        if (password.length() < 5) {
            problems.put("password", "Password have less then 5 symbols");
        } else if (password.length() > 100) {
            problems.put("password", "Password have more then 100 symbols.");
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
}
