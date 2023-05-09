package ru.itis.MyTube.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.entities.Channel;
import ru.itis.MyTube.entities.Video;

@Component
@RequiredArgsConstructor
public class Converter {
    private final UrlCreator urlCreator;

    public ChannelDto from(Channel channel, Page<Video> page) {
        String url = urlCreator.createChannelUrl(String.valueOf(channel.getId()));
        return ChannelDto.builder()
                .id(channel.getId())
                .iconUrl(url)
                .name(channel.getName())
                .info(channel.getInfo())
                .videosPage(page.map(video -> VideoCover.builder()
                        .name(video.getName())
                        .addedDate(video.getAddedDate().toLocalDateTime())
                        .build()))
                .build();
    }
}
