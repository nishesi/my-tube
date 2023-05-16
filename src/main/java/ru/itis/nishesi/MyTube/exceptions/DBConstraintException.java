package ru.itis.nishesi.MyTube.exceptions;

public class DBConstraintException extends RuntimeException {
    public DBConstraintException(String message) {
        super(message);
    }
}
