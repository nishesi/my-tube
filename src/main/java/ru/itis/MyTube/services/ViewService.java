package ru.itis.MyTube.services;

import ru.itis.MyTube.dto.UserDto;
import ru.itis.MyTube.dto.ViewDto;
import ru.itis.MyTube.enums.Reaction;
import ru.itis.MyTube.exceptions.ServiceException;

import java.util.UUID;

public interface ViewService {
    ViewDto getView(UUID videoId, UserDto userDto) throws ServiceException;
    ViewDto updateView(UUID videoId, UserDto userDto, Reaction reaction) throws ServiceException;
}
