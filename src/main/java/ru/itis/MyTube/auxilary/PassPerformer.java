package ru.itis.MyTube.auxilary;

public class PassPerformer {
    public static String hash(String password) {
        return String.valueOf(password.hashCode());
    }
}
