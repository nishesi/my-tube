package ru.itis.MyTube.model;

import lombok.Builder;
import lombok.Data;
import ru.itis.MyTube.dto.VideoCover;

import java.util.UUID;

@Builder
@Data
public class Video {
    private UUID uuid;
    private String videoUrl;
    private VideoCover videoCover;
    private String info;
    private Long likes;
    private Long dislikes;
}
