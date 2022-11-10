package ru.itis.MyTube.model.dto.forms;

import lombok.Builder;
import lombok.Data;

import javax.servlet.http.Part;
import java.io.InputStream;

@Builder
@Data
public class VideoForm {
    private Long channelId;
    private String name;
    private String info;
    private Part iconPart;
    private Part videoPart;
}
