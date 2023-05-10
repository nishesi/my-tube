package ru.itis.MyTube.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.dto.forms.video.UpdateVideoForm;
import ru.itis.MyTube.entities.Channel;
import ru.itis.MyTube.entities.Video;
import ru.itis.MyTube.enums.FileType;

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
                .addedDate(video.getAddedDate().toLocalDateTime())
                .watchUrl(urlCreator.createWatchUrl(video.getUuid().toString()))
                .videoCoverImgUrl(urlCreator.createResourceUrl(FileType.VIDEO_ICON, video.getUuid().toString()))
                .build());
    }

    public VideoDto from(Video video, Page<VideoCover> videoCovers) {
        return VideoDto.builder()
                .uuid(video.getUuid())
                .name(video.getName())
                .info(video.getInfo())
                .addedDate(video.getAddedDate().toLocalDateTime())
                .videoFileUrl(urlCreator.createResourceUrl(FileType.VIDEO, video.getUuid().toString()))
                .channelCover(from(video.getChannel()))
                .additionalVideos(videoCovers)
                .build();
    }

    public ChannelCover from(Channel channel) {
        String channelImgUrl = urlCreator.createResourceUrl(FileType.CHANNEL_ICON, String.valueOf(channel.getId()));
        return ChannelCover.builder()
                .id(channel.getId())
                .name(channel.getName())
                .channelImgUrl(channelImgUrl)
                .build();
    }

    public UpdateVideoForm from(Video video) {
        return UpdateVideoForm.builder()
                .uuid(video.getUuid().toString())
                .name(video.getName())
                .info(video.getInfo())
                .build();
    }
}
