package ru.itis.MyTube.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
public class VideoCover {
    private String videoCoverImgUrl;
    private String name;
    private String channelImgUrl;
    private String channelName;
    private LocalDateTime addedDate;
    private String views;
    private LocalTime duration;
}
