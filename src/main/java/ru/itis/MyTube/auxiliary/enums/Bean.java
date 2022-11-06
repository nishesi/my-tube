package ru.itis.MyTube.auxiliary.enums;

public enum Bean {
    USER_SERVICE("userService"),
    VIDEO_SERVICE("videoService"),
    CHANNEL_SERVICE("channelService"),
    STORAGE("storage"),
    URL_CREATOR("urlCreator"),
    VIDEO_COVER_LIST("videoCoverList"),
    USER("user"),
    APP_LOGO_URL("logoUrl"),
    APP_NAME("appName"),
    COMMON_CSS_URL("commonCssUrl");

    private final String paramName;

    Bean(String paramName) {
        this.paramName = paramName;
    }


    @Override
    public String toString() {
        return paramName;
    }
}
