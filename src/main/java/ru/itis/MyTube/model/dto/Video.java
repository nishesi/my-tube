package ru.itis.MyTube.model.dto;

import lombok.Builder;
import lombok.Data;

import java.nio.file.Path;

@Builder
@Data
public class Video {
    private Long id;
    private VideoCover videoCover;
    private Path source;
    private String info;
    private Long likes;
    private Long dislikes;
}
