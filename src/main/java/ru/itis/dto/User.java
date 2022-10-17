package ru.itis.dto;

import java.time.LocalDate;


public class User {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String sex;
    private String country;

    public User(String login, String password, String firstName,
                String lastName, LocalDate birthDate, String sex, String country) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.country = country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public String getCountry() {
        return country;
    }
}
