package ru.itis.MyTube.model.services.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.Type;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.validators.SearchValidator;
import ru.itis.MyTube.model.dao.interfaces.VideoRepository;
import ru.itis.MyTube.model.dto.ChannelCover;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.forms.VideoForm;
import ru.itis.MyTube.model.services.VideoService;
import ru.itis.MyTube.model.storage.Storage;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final SearchValidator searchValidator;
    private final UrlCreator urlCreator;

    private final Storage storage;

    @Override
    public void addVideo(Long channelId, VideoForm form, InputStream video, InputStream icon) {
        String uuid = UUID.randomUUID().toString();

        ChannelCover channelCover = ChannelCover.builder().id(channelId).build();
        //todo duration
        VideoCover videoCover = VideoCover.builder()
                .name(form.getName())
                .videoCoverImgUrl(urlCreator.create(Type.VIDEO_ICON, uuid))
                .channelCover(channelCover)
                .duration(LocalTime.of(0,0,0))
                .addedDate(LocalDateTime.now())
                .build();

        Video video1 = Video.builder()
                .uuid(UUID.fromString(uuid))
                .videoCover(videoCover)
                .videoUrl(urlCreator.create(Type.VIDEO, uuid))
                .info(form.getInfo())
                .build();

        try {
            videoRepository.addVideo(video1);
        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }

        storage.save(Type.VIDEO_ICON, uuid, icon);
        storage.save(Type.VIDEO, uuid, video);
    }

    @Override
    public VideoCover getVideoCover(UUID uuid) {
        return null;
    }

    @Override
    public Video getVideo(UUID uuid) {
        Optional<Video> video;

        try {
            video = videoRepository.getVideo(uuid);
            video.ifPresent(video1 -> video1.setVideoUrl(urlCreator.create(Type.VIDEO, video1.getUuid().toString())));

        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return video.orElseThrow(() -> new ServiceException("Video not found."));
    }

    @Override
    public List<VideoCover> getRandomVideos() {
        return null;
    }

    @Override
    public List<VideoCover> getPopularVideos() {
        return null;
    }

    @Override
    public List<VideoCover> getVideosByNameSubstring(String substring) {
        try {
            searchValidator.validate(substring);
            List<VideoCover> videoCovers = videoRepository.getVideosByName(substring);

            videoCovers.forEach(videoCover -> {

                videoCover.setVideoCoverImgUrl(
                        urlCreator.create(
                                Type.VIDEO_ICON,
                                videoCover.getUuid().toString()));

                ChannelCover channelCover = videoCover.getChannelCover();
                channelCover.setChannelImgUrl(
                        urlCreator.create(
                                Type.CHANNEL_ICON,
                                channelCover.getId().toString()));
            });

            return videoCovers;

        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

}
