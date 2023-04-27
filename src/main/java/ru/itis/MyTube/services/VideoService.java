package ru.itis.MyTube.services;

import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.User;
import ru.itis.MyTube.model.Video;
import ru.itis.MyTube.model.VideoCover;
import ru.itis.MyTube.dto.forms.VideoForm;

import java.util.List;

public interface VideoService {

    void addVideo(VideoForm videoForm) throws ServiceException, ValidationException;

    void updateVideo(VideoForm videoForm) throws ServiceException, ValidationException;

    void deleteVideo(String videoUuid, Long channelId) throws ServiceException, ValidationException;

    Video getVideo(String videoId) throws ServiceException;

    List<VideoCover> getRandomVideos() throws ServiceException;

    List<VideoCover> getSubscriptionsVideos(User user) throws ServiceException;

    List<VideoCover> getVideosByNameSubstring(String substring) throws ServiceException;

    List<VideoCover> getChannelVideoCovers(Long channelId) throws ServiceException;
}
