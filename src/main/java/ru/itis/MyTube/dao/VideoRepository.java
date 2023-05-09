package ru.itis.MyTube.dao;

import ru.itis.MyTube.model.Video;
import ru.itis.MyTube.dto.VideoCover;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VideoRepository {
    List<VideoCover> getVideosByName(String substring);

    Optional<Video> getVideo(UUID uuid);

    void addVideo(Video video);

    void updateVideo(Video video);
    void deleteVideo(UUID videoUuid);

    List<VideoCover> getChannelVideos(Long channelId);

    List<VideoCover> getRandomVideos();

    List<VideoCover> getSubscribedChannelsVideos(String username);
}
