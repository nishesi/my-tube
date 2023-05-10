package ru.itis.MyTube.services;

import org.springframework.data.domain.Page;
import ru.itis.MyTube.enums.VideoCollectionType;
import ru.itis.MyTube.dto.VideoCover;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.dto.UserDto;

public interface SearchService {
    Page<VideoCover> findVideosByNameSubstring(String substring, int pageInd) throws ServiceException;
    Page<VideoCover> getVideoCollection(VideoCollectionType type, UserDto user, int pagInd) throws ServiceException;
}
