package ru.itis.MyTube.services;

import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.exceptions.ValidationException;
import ru.itis.MyTube.model.Reactions;
import ru.itis.MyTube.dto.forms.ReactionForm;
import ru.itis.MyTube.dto.UserDto;

public interface ReactionService {
    String updateReaction(ReactionForm form) throws ServiceException, ValidationException;
    Reactions getReaction(String videoUuid, UserDto userDto) throws ServiceException;
}
