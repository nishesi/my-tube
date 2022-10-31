package ru.itis.MyTube.auxiliary;

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
