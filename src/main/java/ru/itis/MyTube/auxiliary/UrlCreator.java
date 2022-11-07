package ru.itis.MyTube.auxiliary;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.enums.FileType;

@RequiredArgsConstructor
public class UrlCreator {
    private final String contextPath;

    private final String resourcePath;

    private final String watchPath;

    private final String channelPath;

    public String createResourceUrl(FileType fileType, String id) {
        String url = contextPath + resourcePath + "?id=" + id + "&fileType=";

        return url + fileType.getType();
    }

    public String createWatchUrl(String videoId) {
        return contextPath + watchPath + "?" + FileType.VIDEO.getType() + "=" + videoId;
    }

    public String createChannelUrl(String channelId) {
        return contextPath + channelPath + "?id=" + channelId;
    }
}
