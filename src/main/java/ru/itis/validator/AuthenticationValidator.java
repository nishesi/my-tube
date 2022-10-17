package ru.itis.validator;

import ru.itis.forms.AuthenticationForm;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationValidator {

    public Map<String, String> validate(AuthenticationForm form) {
        Map<String, String> problems = new HashMap<>();

        validateUsername(form.getUsername(), problems);
        validatePassword(form.getPassword(), problems);

        return problems;
    }

    protected void validateUsername(String username, Map<String, String> problems) {
        if (username == null || username.equals("")) {
            problems.put("usernameProblem", "Username can not be empty.");
        }
    }

    protected void validatePassword(String password, Map<String, String> problems) {
        if (password == null || password.equals("")) {
            problems.put("passwordProblem", "Password can not be empty.");
        }
    }
}
