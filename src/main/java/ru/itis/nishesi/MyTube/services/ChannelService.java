package ru.itis.nishesi.MyTube.services;

import ru.itis.nishesi.MyTube.dto.ChannelDto;
import ru.itis.nishesi.MyTube.dto.forms.channel.NewChannelForm;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.dto.UserDto;

public interface ChannelService {
    Long create(NewChannelForm form, UserDto userDto) throws ServiceException;

    ChannelDto getChannel(long id, int pageNum);

    ChannelDto getChannelRegardingUser(long id, int pageNum, UserDto user);
}
