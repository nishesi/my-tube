package ru.itis.MyTube.auxiliary;

public class PassPerformer {
    public static String hash(String password) {
        return String.valueOf(password.hashCode());
    }
}
