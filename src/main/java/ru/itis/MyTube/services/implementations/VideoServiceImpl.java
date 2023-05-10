package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.controllers.VideoCollectionType;
import ru.itis.MyTube.dto.Converter;
import ru.itis.MyTube.dto.VideoCover;
import ru.itis.MyTube.dto.VideoDto;
import ru.itis.MyTube.dto.forms.video.NewVideoForm;
import ru.itis.MyTube.dto.forms.video.UpdateVideoForm;
import ru.itis.MyTube.dto.forms.video.VideoForm;
import ru.itis.MyTube.entities.Video;
import ru.itis.MyTube.entities.View;
import ru.itis.MyTube.entities.enums.Reaction;
import ru.itis.MyTube.exceptions.NotFoundException;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.model.ChannelCover;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.repositories.SubscriptionRepository;
import ru.itis.MyTube.repositories.VideoRepository;
import ru.itis.MyTube.repositories.ViewRepository;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.storage.FileType;
import ru.itis.MyTube.storage.Storage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ViewRepository viewRepository;
    private final Converter converter;
    private final Storage storage;
    private final UrlCreator urlCreator;
    @Value("${spring.data.web.pageable.default-page-size}")
    private int pageSize;

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
//            videoRepository.addVideo(video);

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
//                .videoCover(videoCover)
//                .videoUrl(urlCreator.createResourceUrl(FileType.VIDEO, uuidStr))
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
        Video video = null;
//                videoRepository.getVideo(id)
//                .orElseThrow(() -> new NotFoundException("Video not found."));

        long videoChannelId = -1;
//                video.getVideoCover().getChannelCover().getId();
        if (videoChannelId != userDto.getChannelId())
            throw new ServiceException("You cannot update this video.");

//        if (form.getName() != null) video.getVideoCover().setName(form.getName());
        if (form.getInfo() != null) video.setInfo(form.getInfo());

        try {
//            videoRepository.updateVideo(video);
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
            Video video = null;
//                    videoRepository.getVideo(uuid).orElseThrow(() -> new ServiceException("Video not found."));

//            if (!video.getVideoCover().getChannelCover().getId().equals(channelId)) {
//                throw new ServiceException("You can't delete this video.");
//            }

//            videoRepository.deleteVideo(uuid);
            storage.delete(FileType.VIDEO, uuid.toString());
            storage.delete(FileType.VIDEO_ICON, uuid.toString());

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public ru.itis.MyTube.model.Video getVideo(String videoId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(videoId);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new ServiceException("Couldn't find or load a video.");
        }

        try {
            Optional<Video> videoOpt = null;
//                    videoRepository.getVideo(uuid);
            Video video = videoOpt.orElseThrow(() -> new ServiceException("Video not found."));
//
//            video.setVideoUrl(urlCreator.createResourceUrl(
//                    FileType.VIDEO,
//                    video.getUuid().toString())
//            );
//            ChannelCover channelCover = video.getVideoCover().getChannelCover();
//
//            channelCover.setChannelImgUrl(urlCreator.createResourceUrl(
//                    FileType.CHANNEL_ICON,
//                    channelCover.getId().toString()));

//            return video;
            return null;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public VideoDto getVideo(UUID id, int pageInd) throws ServiceException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Video not found."));
        Page<VideoCover> additionalVideos = this.getVideoCollection(VideoCollectionType.RANDOM, null, pageInd);
        return converter.from(video, additionalVideos);
    }

    @Override
    public VideoDto getVideoRegardingUser(UUID id, int pageInt, UserDto userDto) throws ServiceException {
        VideoDto videoDto = getVideo(id, pageInt);
        Optional<View> viewOptional = viewRepository.findById(id, userDto.getId());
        viewOptional.ifPresent(view -> videoDto.setReaction(view.getReaction()));
        return videoDto;
    }
    @Override
    public List<VideoCover> getVideosByNameSubstring(String substring) {
        try {
            List<VideoCover> videoCovers = null;
//                    videoRepository.getVideosByName(substring);

            setUrls(videoCovers);

            return videoCovers;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public Page<VideoCover> getVideoCollection(VideoCollectionType type, UserDto user, int pageInd) throws ServiceException {
        Pageable pageable = PageRequest.of(pageInd, pageSize, Sort.by("addedDate").descending());

        Page<Video> page = switch (type) {
            case RANDOM -> videoRepository.findAll(pageable);
            case SUBSCRIPTIONS -> {
                if (user == null) throw new ServiceException("User not found.");
                Collection<Long> channelId = subscriptionRepository.findSubscriptionChannelIdByUserId(user.getId());
                yield videoRepository.getByChannelIdIn(channelId, pageable);
            }
        };
        return converter.from(page);
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
