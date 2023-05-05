package ru.itis.MyTube.services;

import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.dto.forms.user.UpdateUserForm;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.dto.forms.user.NewUserForm;
import ru.itis.MyTube.dto.forms.SubscribeForm;

import java.util.UUID;

public interface UserService {
    void save(NewUserForm form) throws ServiceException;

    UserDto get(String username, String password) throws ServiceException;

    void update(UpdateUserForm form, UserDto userDto) throws ServiceException;

    boolean isSubscribed(UserDto userDto, Long channelId) throws ServiceException;

    Byte getUserReaction(UUID videoUuid, String username) throws ServiceException;

    void userChannel(SubscribeForm subscribeForm) throws ServiceException;
}
