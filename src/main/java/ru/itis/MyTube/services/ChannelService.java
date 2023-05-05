package ru.itis.MyTube.services;

import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.dto.forms.channel.NewChannelForm;
import ru.itis.MyTube.model.Channel;
import ru.itis.MyTube.model.UserDto;

public interface ChannelService {
    Channel getChannel(String formId) throws ServiceException;

    Long create(NewChannelForm form, UserDto userDto) throws ServiceException;
}
