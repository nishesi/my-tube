package ru.itis.nishesi.MyTube.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
public class VideoCover {
    private String uuid;
    private String name;
    private String videoCoverImgUrl;
    private ChannelCover channelCover;
    private LocalDateTime addedDate;
    private LocalTime duration;
    private Long views;
}
