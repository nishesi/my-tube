package ru.itis.MyTube.model.services;

import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;

import java.util.List;

public interface ChannelService {
    boolean addVideo(VideoCover videoCover, Video video);

    boolean deleteVideo(Long cover_id);

    boolean updateVideo(VideoCover videoCover, Video video);

    List<VideoCover> getChannelVideoCovers(Long channelId);
}
