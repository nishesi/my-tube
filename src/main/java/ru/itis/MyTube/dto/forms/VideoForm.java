package ru.itis.MyTube.dto.forms;

import lombok.Builder;
import lombok.Data;

import jakarta.servlet.http.Part;

@Builder
@Data
public class VideoForm {
    private Long channelId;
    private String videoUuid;
    private String name;
    private String info;
    private Part iconPart;
    private Part videoPart;
}
