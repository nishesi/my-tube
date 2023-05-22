package ru.itis.nishesi.MyTube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.Duration;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    private String uuid;
    private String name;
    private String info;
    private String videoFileUrl;
    private ChannelCover channelCover;
    private ZonedDateTime addedDate;
    private Duration duration;
    private ViewDto view;
    private Page<VideoCover> additionalVideos;
    private Page<CommentDto> comments;
}
