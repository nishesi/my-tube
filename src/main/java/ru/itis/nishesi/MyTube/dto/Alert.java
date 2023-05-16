package ru.itis.nishesi.MyTube.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.itis.nishesi.MyTube.enums.AlertType;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class Alert implements Serializable {
    private final AlertType type;
    private final String message;

    public static Alert of(AlertType type, String message) {
        return new Alert(type, message);
    }

}
