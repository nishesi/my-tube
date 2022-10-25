package ru.itis.MyTube.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Builder
@Data
public class VideoCover {
    private Long id;
    private String name;
    private byte[] icon;
    private ZonedDateTime addedDate;
    private Video video;
    private Long channelId;
    private LocalDate duration;
}
