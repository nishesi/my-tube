package ru.itis.nishesi.MyTube.exceptions;

public class ExistsException extends RuntimeException {
    public ExistsException(String message) {
        super(message);
    }
}
