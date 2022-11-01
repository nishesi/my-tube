package ru.itis.MyTube.auxiliary;

public enum Attributes {
    USER_REP("userRepository"),
    VIDEO_REP("videoRepository"),
    VIDEO_COVER_LIST("videoCoverList"),
    USER("user"),
    APP_LOGO_URL("logoUrl"),
    APP_NAME("appName");

    private final String paramName;

    Attributes(String paramName) {
        this.paramName = paramName;
    }


    @Override
    public String toString() {
        return paramName;
    }
}
