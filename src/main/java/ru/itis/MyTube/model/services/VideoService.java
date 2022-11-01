package ru.itis.MyTube.model.services;

import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;

import java.util.List;
import java.util.UUID;

public interface VideoService {
    VideoCover getVideoCover(Long id);

    Video getVideo(UUID id);

    List<VideoCover> getRandomVideos();

    List<VideoCover> getPopularVideos();

    List<VideoCover> getVideosByName(String substring);



}
