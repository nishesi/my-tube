package ru.itis.MyTube.services;

import ru.itis.MyTube.dto.forms.user.NewUserForm;
import ru.itis.MyTube.dto.forms.user.UpdateUserForm;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.entities.enums.Reaction;

import java.util.UUID;

public interface UserService {
    void save(NewUserForm form) throws ServiceException;

    void update(UpdateUserForm form) throws ServiceException;

    Reaction getUserReaction(UUID videoUuid, long userId) throws ServiceException;

    void changeSubscription(long channelId, long userId) throws ServiceException;
}
