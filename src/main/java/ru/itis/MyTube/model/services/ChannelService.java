package ru.itis.MyTube.model.services;

import ru.itis.MyTube.model.dto.Channel;
import ru.itis.MyTube.model.dto.forms.ChannelForm;

public interface ChannelService {
    Channel getChannel(String formId);

    Long create(ChannelForm form);
}
