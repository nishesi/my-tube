package ru.itis.MyTube;

public enum Attributes {
    USER_REP("userRepository");

    private final String paramName;

    Attributes(String paramName) {
        this.paramName = paramName;
    }


    @Override
    public String toString() {
        return paramName;
    }
}
