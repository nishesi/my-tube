package ru.itis.MyTube.auxiliary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.storage.FileType;

import static ru.itis.MyTube.controllers.UrlPatterns.*;

@Component
public class UrlCreator {
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public String createResourceUrl(FileType fileType, String id) {
        return contextPath + RESOURCE + "/" + fileType.getType() + "/" + id;
    }

    public String createWatchUrl(String videoId) {
        return contextPath + VIDEO + "/" + videoId;
    }

    public String createChannelUrl(String channelId) {
        return contextPath + CHANNEL + "?id=" + channelId;
    }
}
