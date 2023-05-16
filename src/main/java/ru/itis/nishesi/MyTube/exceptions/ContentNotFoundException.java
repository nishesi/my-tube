package ru.itis.nishesi.MyTube.exceptions;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(String entityName) {
        super(entityName);
    }
}
