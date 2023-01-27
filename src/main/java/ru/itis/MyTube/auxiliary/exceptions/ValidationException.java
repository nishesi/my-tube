package ru.itis.MyTube.auxiliary.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
//@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid parameters")
public class ValidationException extends Exception {
    private final Map<String, String> problems;
}
