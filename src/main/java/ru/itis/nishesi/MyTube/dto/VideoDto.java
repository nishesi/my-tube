package ru.itis.nishesi.MyTube.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import ru.itis.nishesi.MyTube.enums.Reaction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    private UUID uuid;
    private String name;
    private String info;
    private String videoFileUrl;
    private ChannelCover channelCover;
    private LocalDateTime addedDate;
    private LocalTime duration;
    private Long views;
    private Long likes;
    private Long dislikes;
    private Page<VideoCover> additionalVideos;
    private Reaction reaction;
}
