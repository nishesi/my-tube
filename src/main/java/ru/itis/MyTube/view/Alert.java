package ru.itis.MyTube.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class Alert implements Serializable {
    private final AlertType type;
    private final String message;

    public static Alert of(AlertType type, String message) {
        return new Alert(type, message);
    }

    public enum AlertType {
        PRIMARY("alert-primary"),
        SECONDARY("alert-secondary"),
        SUCCESS("alert-success"),
        DANGER("alert-danger"),
        WARNING("alert-warning"),
        INFO("alert-info"),
        LIGHT("alert-light"),
        DARK("alert-dark");

        private final String alertType;

        AlertType(String str) {
            alertType = str;
        }

        public String getAlertType() {
            return alertType;
        }
    }
}
