package ru.itis.MyTube.model.services;

import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.forms.VideoForm;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface VideoService {

    void addVideo(VideoForm videoForm) throws ValidationException;

    Video getVideo(UUID uuid) throws ValidationException;

    List<VideoCover> getRandomVideos();

    List<VideoCover> getPopularVideos();

    List<VideoCover> getSubscriptionsVideos(User user) throws ValidationException;

    List<VideoCover> getVideosByNameSubstring(String substring) throws ValidationException;

}
