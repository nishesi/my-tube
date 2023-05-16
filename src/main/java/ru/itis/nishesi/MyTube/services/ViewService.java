package ru.itis.nishesi.MyTube.services;

import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.dto.ViewDto;
import ru.itis.nishesi.MyTube.enums.Reaction;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;

import java.util.UUID;

public interface ViewService {
    ViewDto getView(UUID videoId, UserDto userDto) throws ServiceException;
    ViewDto updateView(UUID videoId, UserDto userDto, Reaction reaction) throws ServiceException;
}
