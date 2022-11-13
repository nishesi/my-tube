package ru.itis.MyTube.model.services;

import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.dto.forms.VideoForm;

import java.util.List;
import java.util.UUID;

public interface VideoService {

    void addVideo(VideoForm videoForm) throws ValidationException;

    void updateVideo(VideoForm videoForm) throws ValidationException;

    void deleteVideo(String videoUuid, Long channelId) throws ValidationException;

    Video getVideo(UUID uuid) throws ValidationException;

    List<VideoCover> getRandomVideos();

    List<VideoCover> getPopularVideos();

    List<VideoCover> getSubscriptionsVideos(User user) throws ValidationException;

    List<VideoCover> getVideosByNameSubstring(String substring) throws ValidationException;

    List<VideoCover> getChannelVideoCovers(Long channelId);
}
