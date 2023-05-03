package ru.itis.MyTube.services;

import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.dto.forms.user.UpdateUserForm;
import ru.itis.MyTube.model.User;
import ru.itis.MyTube.dto.forms.user.NewUserForm;
import ru.itis.MyTube.dto.forms.SubscribeForm;

import java.util.UUID;

public interface UserService {
    void save(NewUserForm form) throws ServiceException;

    User get(String username, String password) throws ServiceException;

    void update(UpdateUserForm form, User user) throws ServiceException;

    boolean isSubscribed(User user, Long channelId) throws ServiceException;

    Byte getUserReaction(UUID videoUuid, String username) throws ServiceException;

    void userChannel(SubscribeForm subscribeForm) throws ServiceException;
}
