package ru.itis.MyTube.enums;

import lombok.Getter;

@Getter
public enum AlertType {
    PRIMARY("alert-primary"),
    SECONDARY("alert-secondary"),
    SUCCESS("alert-success"),
    DANGER("alert-danger"),
    WARNING("alert-warning"),
    INFO("alert-info"),
    LIGHT("alert-light"),
    DARK("alert-dark");

    private final String str;

    AlertType(String str) {
        this.str = str;
    }
}
