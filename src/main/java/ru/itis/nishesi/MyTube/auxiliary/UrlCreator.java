package ru.itis.nishesi.MyTube.auxiliary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.nishesi.MyTube.enums.FileType;

@Component
public class UrlCreator {
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public String createResourceUrl(FileType fileType, String id) {
        return contextPath + "/resource/" + fileType.getType() + "/" + id;
    }
}
