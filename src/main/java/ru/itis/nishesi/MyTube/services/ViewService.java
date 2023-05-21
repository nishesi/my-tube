package ru.itis.nishesi.MyTube.services;

import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.dto.ViewDto;
import ru.itis.nishesi.MyTube.dto.rest.ReactionForm;
import ru.itis.nishesi.MyTube.exceptions.ContentNotFoundException;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;

import java.util.List;
import java.util.UUID;

public interface ViewService {
    ViewDto getView(UUID videoId) throws ServiceException, ContentNotFoundException;
    ViewDto getView(UUID videoId, UserDto userDto) throws ServiceException, ContentNotFoundException;
    ViewDto updateView(ReactionForm reactionForm, UserDto userDto) throws ServiceException;

    ViewDto createView(ReactionForm reactionForm, UserDto userDto);
    void deleteView(UUID videoId, UserDto userDto);

    List<Long> getViews(List<UUID> videoIds);
}
