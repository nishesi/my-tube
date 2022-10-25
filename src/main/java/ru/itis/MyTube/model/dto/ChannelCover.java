package ru.itis.MyTube.model.dto;

import lombok.Builder;
import lombok.Data;

import java.nio.channels.Channel;


@Builder
@Data
public class ChannelCover {
    private Long id;
    private String name;
    private Channel channel;
}
