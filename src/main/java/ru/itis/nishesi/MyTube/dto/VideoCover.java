package ru.itis.nishesi.MyTube.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.ZonedDateTime;

@Builder
@Data
public class VideoCover {
    private String uuid;
    private String name;
    private String videoCoverImgUrl;
    private ChannelCover channelCover;
    private ZonedDateTime addedDate;
    private Duration duration;
    private Long views;
}
