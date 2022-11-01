package ru.itis.MyTube.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@Data
public class VideoCover {
    private UUID uuid;
    private String videoCoverImgUrl;
    private String name;
    private ChannelCover channelCover;
    private LocalDateTime addedDate;
    private LocalTime duration;
    private String views;
}
