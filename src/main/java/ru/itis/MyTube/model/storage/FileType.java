package ru.itis.MyTube.model.storage;

public enum FileType {
    VIDEO("v"),
    VIDEO_ICON("vi"),
    CHANNEL_ICON("ci"),
    USER_ICON("ui");
    private final String type;
    FileType(String n) {
        type = n;
    }

    public String getType() {
        return type;
    }
}
