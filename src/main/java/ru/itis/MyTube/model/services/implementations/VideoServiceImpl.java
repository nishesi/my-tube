package ru.itis.MyTube.model.services.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.enums.FileType;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.auxiliary.validators.SearchValidator;
import ru.itis.MyTube.auxiliary.validators.VideoValidator;
import ru.itis.MyTube.model.dao.ChannelRepository;
import ru.itis.MyTube.model.dao.VideoRepository;
import ru.itis.MyTube.model.dto.ChannelCover;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.dto.forms.VideoForm;
import ru.itis.MyTube.model.services.VideoService;
import ru.itis.MyTube.model.storage.Storage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    
    private final ChannelRepository channelRepository;
    private final SearchValidator searchValidator;
    private final UrlCreator urlCreator;

    private final Storage storage;

    private final VideoValidator videoValidator;

    @Override
    public void addVideo(VideoForm form) throws ValidationException {
        videoValidator.validate(form);

        String uuidStr = UUID.randomUUID().toString();
        ChannelCover channelCover = ChannelCover.builder().id(form.getChannelId()).build();

        VideoCover videoCover = VideoCover.builder()
                .name(form.getName())
                .videoCoverImgUrl(urlCreator.createResourceUrl(FileType.VIDEO_ICON, uuidStr))
                .channelCover(channelCover)
                .duration(LocalTime.of(0, 0, 0))
                .addedDate(LocalDateTime.now())
                .build();

        Video video1 = Video.builder()
                .uuid(UUID.fromString(uuidStr))
                .videoCover(videoCover)
                .videoUrl(urlCreator.createResourceUrl(FileType.VIDEO, uuidStr))
                .info(form.getInfo())
                .build();

        try {
            videoRepository.addVideo(video1);

            storage.save(FileType.VIDEO_ICON, uuidStr, form.getIconPart().getInputStream());
            storage.save(FileType.VIDEO, uuidStr, form.getVideoPart().getInputStream());

        } catch (IOException | RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Video getVideo(UUID uuid) {
        Optional<Video> video;

        try {
            video = videoRepository.getVideo(uuid);
            video.ifPresent(video1 -> video1.setVideoUrl(urlCreator.createResourceUrl(FileType.VIDEO, video1.getUuid().toString())));

        } catch (RuntimeException ex) {
            throw new ServiceException(ex);
        }

        return video.orElseThrow(() -> new ServiceException("Video not found."));
    }

    @Override
    public List<VideoCover> getRandomVideos() {
        try {
            List<VideoCover> videoCovers = videoRepository.getRandomVideos();
            setUrls(videoCovers);
            return videoCovers;
        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<VideoCover> getPopularVideos() {
        return null;
    }

    @Override
    public List<VideoCover> getSubscriptionsVideos(User user) throws ValidationException {
        if (Objects.isNull(user.getUsername()) || "".equals(user.getUsername())) {
            throw new ValidationException(Collections.singletonMap("username", "Username is void"));
        }
        try {
            List<VideoCover> videoCovers = videoRepository.getSubscribedChannelsVideos(user.getUsername());
            setUrls(videoCovers);

            return videoCovers;
        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<VideoCover> getVideosByNameSubstring(String substring) {
        try {
            searchValidator.validate(substring);
            List<VideoCover> videoCovers = videoRepository.getVideosByName(substring);

            setUrls(videoCovers);

            return videoCovers;

        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<VideoCover> getChannelVideoCovers(Long channelId) {
        try {
            List<VideoCover> channelVideos = videoRepository.getChannelVideos(channelId);
            setUrls(channelVideos);

            return channelVideos;
        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    private void setUrls(List<VideoCover> videoCovers) {
        videoCovers.forEach(videoCover -> {

            videoCover.setVideoCoverImgUrl(
                    urlCreator.createResourceUrl(
                            FileType.VIDEO_ICON,
                            videoCover.getUuid().toString()));
            videoCover.setWatchUrl(urlCreator.createWatchUrl(videoCover.getUuid().toString()));

            ChannelCover channelCover = videoCover.getChannelCover();
            channelCover.setChannelImgUrl(
                    urlCreator.createResourceUrl(
                            FileType.CHANNEL_ICON,
                            channelCover.getId().toString()));
            channelCover.setChannelUrl(urlCreator.createChannelUrl(channelCover.getId().toString()));
        });
    }
}
