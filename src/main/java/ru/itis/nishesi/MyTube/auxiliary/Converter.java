package ru.itis.nishesi.MyTube.auxiliary;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.itis.nishesi.MyTube.dto.*;
import ru.itis.nishesi.MyTube.dto.forms.video.UpdateVideoForm;
import ru.itis.nishesi.MyTube.entities.Channel;
import ru.itis.nishesi.MyTube.entities.User;
import ru.itis.nishesi.MyTube.entities.Video;
import ru.itis.nishesi.MyTube.enums.FileType;

import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Converter {
    private final UrlCreator urlCreator;

    public ChannelDto from(Channel channel, Page<Video> page, List<Long> views) {
        String url = urlCreator.createResourceUrl(FileType.CHANNEL_ICON, String.valueOf(channel.getId()));
        return ChannelDto.builder()
                .id(channel.getId())
                .iconUrl(url)
                .name(channel.getName())
                .info(channel.getInfo())
                .videosPage(from(page, views,  false))
                .build();
    }

    public Page<VideoCover> from(Page<Video> page, Iterable<Long> views, boolean setChannel) {
        Iterator<Long> iterator = views.iterator();
        return page.map(video -> {
            VideoCover.VideoCoverBuilder builder = VideoCover.builder()
                    .uuid(video.getUuid().toString())
                    .name(video.getName())
                    .views(iterator.next())
                    .addedDate(video.getAddedDate())
                    .duration(video.getDuration())
                    .videoCoverImgUrl(urlCreator.createResourceUrl(FileType.VIDEO_ICON, video.getUuid().toString()));
            if (setChannel)
                builder.channelCover(from(video.getChannel()));
            return builder.build();
        });
    }

    public VideoDto from(Video video, Page<VideoCover> videoCovers, ViewDto viewDto) {
        String videoFileUrl = urlCreator.createResourceUrl(FileType.VIDEO, video.getUuid().toString());
        return VideoDto.builder()
                .uuid(video.getUuid().toString())
                .name(video.getName())
                .info(video.getInfo())
                .videoFileUrl(videoFileUrl)
                .channelCover(from(video.getChannel()))
                .addedDate(video.getAddedDate())
                .duration(video.getDuration())
                .view(viewDto)
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

    public UserDto from(User user) {
        String iconUrl = urlCreator.createResourceUrl(FileType.USER_ICON, String.valueOf(user.getId()));
        Long channelId = user.getChannel().map(Channel::getId).orElse(null);

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthdate(user.getBirthdate())
                .country(user.getCountry())
                .channelId(channelId)
                .userImgUrl(iconUrl)
                .build();
    }
}
