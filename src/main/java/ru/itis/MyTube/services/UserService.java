package ru.itis.MyTube.services;

import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.dto.forms.user.UpdateUserForm;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.dto.forms.user.NewUserForm;
import ru.itis.MyTube.dto.forms.SubscribeForm;

import java.util.UUID;

public interface UserService {
    void save(NewUserForm form) throws ServiceException;

    void update(UpdateUserForm form, UserDto userDto) throws ServiceException;

    Byte getUserReaction(UUID videoUuid, String username) throws ServiceException;

    void changeSubscription(SubscribeForm subscribeForm) throws ServiceException;
}
