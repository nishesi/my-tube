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

    public static FileType from(String type) {
        for (FileType s : FileType.values()) {
            if (s.type.equals(type)) {
                return s;
            }
        }
        throw new UnknownFileTypeException("unknown file type: " + type);
    }

    public static class UnknownFileTypeException extends RuntimeException {
        public UnknownFileTypeException(String message) {
            super(message);
        }
    }
}
