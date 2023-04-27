package ru.itis.MyTube.dao;

import ru.itis.MyTube.model.Channel;
import ru.itis.MyTube.model.ChannelCover;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository {
    List<ChannelCover> getSubscribedChannels(String username);

    Optional<Channel> get(Long id);
    Optional<ChannelCover> get(String owner_username);
    void create(Channel channel);
}
