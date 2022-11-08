package ru.itis.MyTube.model.dao.interfaces;

import java.util.List;

public interface ChannelRepository {
    List<Long> getSubscribedChannelsId(String username);
}
