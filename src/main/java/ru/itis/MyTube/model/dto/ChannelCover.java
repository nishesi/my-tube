package ru.itis.MyTube.model.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ChannelCover {
    //name of the icon file
    private Long id;
    private String name;
    private String channelImgUrl;
}
