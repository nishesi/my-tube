package ru.itis.MyTube.services;

import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.dto.User;
import ru.itis.MyTube.dto.forms.RegistrationForm;
import ru.itis.MyTube.dto.forms.SubscribeForm;
import ru.itis.MyTube.dto.forms.UserUpdateForm;

import java.util.UUID;

public interface UserService {
    void save(RegistrationForm form) throws ServiceException, ValidationException;

    User get(String username, String password) throws ServiceException, ValidationException;

    void update(UserUpdateForm form, User user) throws ServiceException, ValidationException;

    boolean isSubscribed(User user, Long channelId) throws ServiceException;

    Byte getUserReaction(UUID videoUuid, String username) throws ServiceException;

    void userChannel(SubscribeForm subscribeForm) throws ServiceException;
}
