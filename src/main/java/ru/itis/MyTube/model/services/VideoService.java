package ru.itis.MyTube.model.services;

import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.forms.VideoForm;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface VideoService {

    void addVideo(Long channelId, VideoForm videoForm, InputStream video, InputStream icon);
    VideoCover getVideoCover(UUID uuid);

    Video getVideo(UUID uuid);

    List<VideoCover> getRandomVideos();

    List<VideoCover> getPopularVideos();

    List<VideoCover> getVideosByNameSubstring(String substring);



}
