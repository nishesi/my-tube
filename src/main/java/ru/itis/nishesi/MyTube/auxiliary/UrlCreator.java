package ru.itis.nishesi.MyTube.auxiliary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.nishesi.MyTube.enums.FileType;

import static ru.itis.nishesi.MyTube.controllers.UrlPatterns.*;

@Component
public class UrlCreator {
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public String createResourceUrl(FileType fileType, String id) {
        return contextPath + RESOURCE + "/" + fileType.getType() + "/" + id;
    }

    public String createChannelUrl(String channelId) {
        return contextPath + CHANNEL + "?id=" + channelId;
    }
}
