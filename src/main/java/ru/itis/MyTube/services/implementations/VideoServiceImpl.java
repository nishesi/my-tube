package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.exceptions.NotFoundException;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.dao.VideoRepository;
import ru.itis.MyTube.dto.forms.video.NewVideoForm;
import ru.itis.MyTube.dto.forms.video.UpdateVideoForm;
import ru.itis.MyTube.dto.forms.video.VideoForm;
import ru.itis.MyTube.model.ChannelCover;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.model.Video;
import ru.itis.MyTube.dto.VideoCover;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.storage.FileType;
import ru.itis.MyTube.storage.Storage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final Storage storage;
    private final UrlCreator urlCreator;

    @Override
    public void addVideo(NewVideoForm form, UserDto userDto) {
        String uuid = UUID.randomUUID().toString();
        VideoForm videoForm = VideoForm.builder()
                .channelId(userDto.getChannelId())
                .name(form.getName())
                .info(form.getInfo())
                .build();
        Video video = transferToVideo(videoForm, uuid);

        try {
            videoRepository.addVideo(video);

            storage.save(FileType.VIDEO_ICON, uuid, form.getIconFile().getInputStream());
            storage.save(FileType.VIDEO, uuid, form.getVideoFile().getInputStream());

        } catch (IOException | RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private Video transferToVideo(VideoForm form, String uuidStr) {
        ChannelCover channelCover = ChannelCover.builder().id(form.getChannelId()).build();

        VideoCover videoCover = VideoCover.builder()
                .name(form.getName())
                .videoCoverImgUrl(urlCreator.createResourceUrl(FileType.VIDEO_ICON, uuidStr))
                .channelCover(channelCover)
                .duration(LocalTime.of(0, 0, 0))
                .addedDate(LocalDateTime.now())
                .build();

        return Video.builder()
                .uuid(UUID.fromString(uuidStr))
                .videoCover(videoCover)
                .videoUrl(urlCreator.createResourceUrl(FileType.VIDEO, uuidStr))
                .info(form.getInfo())
                .build();
    }

    @Override
    public void updateVideo(UpdateVideoForm form, UserDto userDto) {
        UUID id;
        try {
            id = UUID.fromString(form.getUuid());
        } catch (RuntimeException ex) {
            throw new ServiceException("invalid video id");
        }
        Video video = videoRepository.getVideo(id)
                .orElseThrow(() -> new NotFoundException("Video not found."));

        long videoChannelId = video.getVideoCover().getChannelCover().getId();
        if (videoChannelId != userDto.getChannelId())
            throw new ServiceException("You cannot update this video.");

        if (form.getName() != null) video.getVideoCover().setName(form.getName());
        if (form.getInfo() != null) video.setInfo(form.getInfo());

        try {
            videoRepository.updateVideo(video);
            MultipartFile icon = form.getIconFile();
            if (!icon.isEmpty())
                storage.save(FileType.VIDEO_ICON, form.getUuid(), icon.getInputStream());

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public void deleteVideo(String videoUuid, Long channelId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(videoUuid);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new ServiceException("Invalid video id.");
        }
        try {
            Video video = videoRepository.getVideo(uuid).orElseThrow(() -> new ServiceException("Video not found."));

            if (!video.getVideoCover().getChannelCover().getId().equals(channelId)) {
                throw new ServiceException("You can't delete this video.");
            }

            videoRepository.deleteVideo(uuid);
            storage.delete(FileType.VIDEO, uuid.toString());
            storage.delete(FileType.VIDEO_ICON, uuid.toString());

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public Video getVideo(String videoId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(videoId);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new ServiceException("Couldn't find or load a video.");
        }

        try {
            Optional<Video> videoOpt = videoRepository.getVideo(uuid);
            Video video = videoOpt.orElseThrow(() -> new ServiceException("Video not found."));

            video.setVideoUrl(urlCreator.createResourceUrl(
                    FileType.VIDEO,
                    video.getUuid().toString())
            );
            ChannelCover channelCover = video.getVideoCover().getChannelCover();

            channelCover.setChannelImgUrl(urlCreator.createResourceUrl(
                    FileType.CHANNEL_ICON,
                    channelCover.getId().toString()));

            return video;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public List<VideoCover> getRandomVideos() {
        try {
            List<VideoCover> videoCovers = videoRepository.getRandomVideos();
            setUrls(videoCovers);

            return videoCovers;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public List<VideoCover> getSubscriptionsVideos(UserDto userDto) {
        if (Objects.isNull(userDto)) {
            throw new ServiceException("You not authorized.");
        }
        try {
            List<VideoCover> videoCovers = videoRepository.getSubscribedChannelsVideos(userDto.getEmail());
            setUrls(videoCovers);

            return videoCovers;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public List<VideoCover> getVideosByNameSubstring(String substring) {
        try {
            List<VideoCover> videoCovers = videoRepository.getVideosByName(substring);

            setUrls(videoCovers);

            return videoCovers;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public List<VideoCover> getChannelVideoCovers(Long channelId) {

        try {
            List<VideoCover> channelVideos = videoRepository.getChannelVideos(channelId);
            setUrls(channelVideos);

            return channelVideos;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
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
