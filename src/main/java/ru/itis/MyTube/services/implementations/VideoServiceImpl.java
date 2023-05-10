package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.MyTube.controllers.VideoCollectionType;
import ru.itis.MyTube.dto.Converter;
import ru.itis.MyTube.dto.VideoCover;
import ru.itis.MyTube.dto.VideoDto;
import ru.itis.MyTube.dto.forms.video.NewVideoForm;
import ru.itis.MyTube.dto.forms.video.UpdateVideoForm;
import ru.itis.MyTube.entities.Channel;
import ru.itis.MyTube.entities.Video;
import ru.itis.MyTube.entities.View;
import ru.itis.MyTube.exceptions.NotFoundException;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.dto.UserDto;
import ru.itis.MyTube.repositories.VideoRepository;
import ru.itis.MyTube.repositories.ViewRepository;
import ru.itis.MyTube.services.SearchService;
import ru.itis.MyTube.services.VideoService;
import ru.itis.MyTube.storage.FileType;
import ru.itis.MyTube.storage.Storage;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final ViewRepository viewRepository;
    private final SearchService searchService;
    private final Converter converter;
    private final Storage storage;
    private final Clock clock;

    @Override
    public void addVideo(NewVideoForm form, UserDto userDto) {
        Channel channel = Channel.builder().id(userDto.getChannelId()).build();
        ZonedDateTime zonedDateTime = ZonedDateTime.now(clock);

        Video newVideo = Video.builder()
                .name(form.getName())
                .info(form.getInfo())
                .channel(channel)
                .addedDate(zonedDateTime)
                .duration(LocalTime.of(0, 0))
                .build();

        try {
            String id = videoRepository.save(newVideo).getUuid().toString();

            storage.save(FileType.VIDEO_ICON, id, form.getIconFile().getInputStream());
            storage.save(FileType.VIDEO, id, form.getVideoFile().getInputStream());

        } catch (IOException | RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void updateVideo(UpdateVideoForm form, UserDto userDto) {
        UUID id = UUID.fromString(form.getUuid());
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Video not found."));

        if (video.getChannel().getId() != userDto.getChannelId())
            throw new ServiceException("Have not an access.");

        if (form.getName() != null) video.setName(form.getName());
        if (form.getInfo() != null) video.setInfo(form.getInfo());

        try {
            videoRepository.save(video);
            MultipartFile icon = form.getIconFile();
            if (!icon.isEmpty())
                storage.save(FileType.VIDEO_ICON, form.getUuid(), icon.getInputStream());

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public void deleteVideo(UUID videoId, UserDto userDto) {
        try {
            Video video = videoRepository.findById(videoId)
                    .orElseThrow(() -> new NotFoundException("Video not found."));

            if (video.getChannel().getId() != userDto.getChannelId())
                throw new ServiceException("Have not an access.");

            videoRepository.delete(video);
            storage.delete(FileType.VIDEO, videoId.toString());
            storage.delete(FileType.VIDEO_ICON, videoId.toString());

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public UpdateVideoForm getVideoForUpdate(UUID id) {
        try {
            Video video = videoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Video not found"));
            return converter.from(video);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public VideoDto getVideo(UUID id, int pageInd) throws ServiceException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Video not found."));
        Page<VideoCover> additionalVideos = searchService.getVideoCollection(VideoCollectionType.RANDOM, null, pageInd);
        return converter.from(video, additionalVideos);
    }

    @Override
    public VideoDto getVideoRegardingUser(UUID id, int pageInt, UserDto userDto) throws ServiceException {
        VideoDto videoDto = getVideo(id, pageInt);
        Optional<View> viewOptional = viewRepository.findById(id, userDto.getId());
        viewOptional.ifPresent(view -> videoDto.setReaction(view.getReaction()));
        return videoDto;
    }
}
