package ru.itis.MyTube.services;

import ru.itis.MyTube.dto.VideoDto;
import ru.itis.MyTube.dto.forms.video.NewVideoForm;
import ru.itis.MyTube.dto.forms.video.UpdateVideoForm;
import ru.itis.MyTube.exceptions.ContentNotFoundException;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.dto.UserDto;

import java.util.UUID;

public interface VideoService {

    void addVideo(NewVideoForm form, UserDto userDto) throws ServiceException;

    VideoDto getVideo(String id, int pageInd) throws ServiceException;

    void updateVideo(UpdateVideoForm form, UserDto userDto) throws ServiceException, ContentNotFoundException;

    void deleteVideo(UUID videoId, UserDto userDto) throws ServiceException;

    UpdateVideoForm getVideoForUpdate(UUID id) throws ServiceException;

    VideoDto getVideoRegardingUser(String id, int pageInt, UserDto userDto) throws ServiceException;
}
