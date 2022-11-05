package ru.itis.MyTube.auxiliary;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UrlCreator {
    private final String contextPath;

    public String create(Type type, String id) {
        String url = contextPath + "/resource?id=" + id + "&type=";
        if (type == Type.VIDEO) {
            return url + "v";
        } else if (type == Type.VIDEO_ICON) {
            return url + "vi";
        } else if (type == Type.CHANNEL_ICON) {
            return url + "ci";
        } else if (type == Type.USER_ICON){
            return url + "ui";
        } else {
            return "unknown type";
        }
    }
}
