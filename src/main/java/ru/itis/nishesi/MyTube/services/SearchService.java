package ru.itis.nishesi.MyTube.services;

import org.springframework.data.domain.Page;
import ru.itis.nishesi.MyTube.enums.VideoCollectionType;
import ru.itis.nishesi.MyTube.dto.VideoCover;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.dto.UserDto;

public interface SearchService {
    Page<VideoCover> findVideosByNameSubstring(String substring, int pageInd) throws ServiceException;
    Page<VideoCover> getVideoCollection(VideoCollectionType type, UserDto user, int pagInd) throws ServiceException;
}
