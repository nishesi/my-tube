package ru.itis.nishesi.MyTube.services;

import ru.itis.nishesi.MyTube.dto.forms.user.NewUserForm;
import ru.itis.nishesi.MyTube.dto.forms.user.UpdateUserForm;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;

public interface UserService {
    void save(NewUserForm form) throws ServiceException;

    void update(UpdateUserForm form) throws ServiceException;

    void changeSubscription(long channelId, long userId) throws ServiceException;
}
