package ru.itis.MyTube.services;

import org.springframework.data.domain.Page;
import ru.itis.MyTube.controllers.VideoCollectionType;
import ru.itis.MyTube.dto.VideoCover;
import ru.itis.MyTube.dto.VideoDto;
import ru.itis.MyTube.dto.forms.video.NewVideoForm;
import ru.itis.MyTube.dto.forms.video.UpdateVideoForm;
import ru.itis.MyTube.exceptions.NotFoundException;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.model.Video;

import java.util.List;
import java.util.UUID;

public interface VideoService {

    void addVideo(NewVideoForm form, UserDto userDto) throws ServiceException;

    void updateVideo(UpdateVideoForm form, UserDto userDto) throws ServiceException, NotFoundException;

    void deleteVideo(String videoUuid, Long channelId) throws ServiceException;

    Video getVideo(String videoId) throws ServiceException;
    VideoDto getVideo(UUID id, int pageInd) throws ServiceException;
    VideoDto getVideoRegardingUser(UUID id, int pageInt, UserDto userDto) throws ServiceException;

    List<VideoCover> getVideosByNameSubstring(String substring) throws ServiceException;

    Page<VideoCover> getVideoCollection(VideoCollectionType type, UserDto user, int pagInd) throws ServiceException;
}
