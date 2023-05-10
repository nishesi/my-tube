package ru.itis.MyTube.services;

import ru.itis.MyTube.dto.ChannelDto;
import ru.itis.MyTube.dto.forms.channel.NewChannelForm;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.dto.UserDto;

public interface ChannelService {
    ChannelDto getChannel(long id, int pageNum);

    ChannelDto getChannelRegardingUser(long id, int pageNum, UserDto user);

    Long create(NewChannelForm form, UserDto userDto) throws ServiceException;
}
