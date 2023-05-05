package ru.itis.MyTube.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Channel {
    private Long id;
    private ChannelCover channelCover;
    private UserDto owner;
    private Long countOfSubscribers;
    private String info;
}
