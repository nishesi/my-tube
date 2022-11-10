package ru.itis.MyTube.model.services;

import ru.itis.MyTube.model.dto.Channel;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;

import java.util.List;

public interface ChannelService {
    Channel getChannel(String formId);

    List<VideoCover> getChannelVideoCovers(Long channelId);
}
