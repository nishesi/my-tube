package ru.itis.forms;

public class AuthenticationForm {

    private String username;

    private String password;

    public AuthenticationForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
