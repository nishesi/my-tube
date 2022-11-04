package ru.itis.MyTube.auxiliary;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UrlCreator {
    private final String contextPath;

    public String create(Type type, String id) {
        if (type == Type.VIDEO) {
            return contextPath + "/resource?v=" + id;
        } else if (type == Type.VIDEO_ICON) {
            return contextPath + "/resource?vi=" + id;
        } else if (type == Type.CHANNEL_ICON) {
            return contextPath + "/resource?ci=" + id;
        } else {
            return "unknown";
        }
    }

    public enum Type {
        VIDEO,
        VIDEO_ICON,
        CHANNEL_ICON,
        USER_ICON
    }
}
