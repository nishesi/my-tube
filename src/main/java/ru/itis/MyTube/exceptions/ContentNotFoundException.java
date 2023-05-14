package ru.itis.MyTube.exceptions;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(String entityName) {
        super(entityName);
    }
}
