package ru.itis.MyTube.model.dao.interfaces;

import ru.itis.MyTube.model.dto.Channel;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository {
    List<Long> getSubscribedChannelsId(String username);
    Optional<Channel> get(Long id);
}
