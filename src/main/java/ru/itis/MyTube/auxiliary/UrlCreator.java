package ru.itis.MyTube.auxiliary;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.enums.FileType;

@RequiredArgsConstructor
public class UrlCreator {
    private final String contextPath;

    public String create(FileType fileType, String id) {
        String url = contextPath + "/resource?id=" + id + "&fileType=";
        if (fileType == FileType.VIDEO) {
            return url + "v";
        } else if (fileType == FileType.VIDEO_ICON) {
            return url + "vi";
        } else if (fileType == FileType.CHANNEL_ICON) {
            return url + "ci";
        } else if (fileType == FileType.USER_ICON){
            return url + "ui";
        } else {
            return "unknown FileType";
        }

    }
}
