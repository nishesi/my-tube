package ru.itis.MyTube.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ValidationException extends Exception {
    private final Map<String, String> problems;
}
