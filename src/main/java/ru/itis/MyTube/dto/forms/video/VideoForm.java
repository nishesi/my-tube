package ru.itis.MyTube.dto.forms.video;

import lombok.Builder;
import lombok.Data;

import jakarta.servlet.http.Part;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoForm {
    private Long channelId;
    private String videoUuid;
    private String name;
    private String info;
    private Part iconPart;
    private Part videoPart;
}
