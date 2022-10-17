package ru.itis.forms;

public class RegistrationForm {
    private String login;
    private String password;
    private String passwordRepeat;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String sex;
    private String country;
    private String agreement;

    public RegistrationForm(String login, String password, String passwordRepeat,
                            String firstName, String lastName, String birthDate,
                            String sex, String country, String agreement) {
        this.login = login;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.country = country;
        this.agreement = agreement;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public String getCountry() {
        return country;
    }

    public String getAgreement() {
        return agreement;
    }
}
