package ru.itis.MyTube.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.entities.Channel;
import ru.itis.MyTube.entities.Video;
import ru.itis.MyTube.storage.FileType;

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
                .videosPage(from(page))
                .build();
    }

    public Page<VideoCover> from(Page<Video> page) {
        return page.map(video -> VideoCover.builder()
                .uuid(video.getUuid())
                .name(video.getName())
                .videoCoverImgUrl(urlCreator.createResourceUrl(FileType.VIDEO_ICON, video.getUuid().toString()))
                .addedDate(video.getAddedDate().toLocalDateTime())
                .build());
    }
}
