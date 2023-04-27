package ru.itis.MyTube.services;

import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.User;
import ru.itis.MyTube.dto.forms.NewUserForm;
import ru.itis.MyTube.dto.forms.SubscribeForm;
import ru.itis.MyTube.dto.forms.UserUpdateForm;

import java.util.UUID;

public interface UserService {
    void save(NewUserForm form) throws ServiceException;

    User get(String username, String password) throws ServiceException;

    void update(UserUpdateForm form, User user) throws ServiceException;

    boolean isSubscribed(User user, Long channelId) throws ServiceException;

    Byte getUserReaction(UUID videoUuid, String username) throws ServiceException;

    void userChannel(SubscribeForm subscribeForm) throws ServiceException;
}
