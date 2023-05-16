package ru.itis.nishesi.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.nishesi.MyTube.enums.VideoCollectionType;
import ru.itis.nishesi.MyTube.dto.Converter;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.dto.VideoCover;
import ru.itis.nishesi.MyTube.dto.VideoDto;
import ru.itis.nishesi.MyTube.dto.forms.video.NewVideoForm;
import ru.itis.nishesi.MyTube.dto.forms.video.UpdateVideoForm;
import ru.itis.nishesi.MyTube.entities.Channel;
import ru.itis.nishesi.MyTube.entities.Video;
import ru.itis.nishesi.MyTube.entities.View;
import ru.itis.nishesi.MyTube.exceptions.ContentNotFoundException;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.repositories.VideoRepository;
import ru.itis.nishesi.MyTube.repositories.ViewRepository;
import ru.itis.nishesi.MyTube.services.SearchService;
import ru.itis.nishesi.MyTube.services.VideoService;
import ru.itis.nishesi.MyTube.enums.FileType;
import ru.itis.nishesi.MyTube.services.FileService;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final ViewRepository viewRepository;
    private final SearchService searchService;
    private final Converter converter;
    private final FileService fileService;
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

            fileService.save(FileType.VIDEO_ICON, id, form.getIconFile().getInputStream());
            fileService.save(FileType.VIDEO, id, form.getVideoFile().getInputStream());

        } catch (IOException | RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void updateVideo(UpdateVideoForm form, UserDto userDto) {
        UUID id = UUID.fromString(form.getUuid());
        Video video = videoRepository.findById(id).orElseThrow();

        if (video.getChannel().getId() != userDto.getChannelId())
            throw new ServiceException("Have not an access.");

        if (form.getName() != null) video.setName(form.getName());
        if (form.getInfo() != null) video.setInfo(form.getInfo());

        try {
            videoRepository.save(video);
            MultipartFile icon = form.getIconFile();
            if (!icon.isEmpty())
                fileService.save(FileType.VIDEO_ICON, form.getUuid(), icon.getInputStream());

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public void deleteVideo(UUID videoId, UserDto userDto) {
        try {
            Video video = videoRepository.findById(videoId).orElseThrow();

            if (video.getChannel().getId() != userDto.getChannelId())
                throw new ServiceException("Have not an access.");

            videoRepository.delete(video);
            fileService.delete(FileType.VIDEO, videoId.toString());
            fileService.delete(FileType.VIDEO_ICON, videoId.toString());

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public UpdateVideoForm getVideoForUpdate(UUID id) {
        try {
            Video video = videoRepository.findById(id).orElseThrow();
            return converter.from(video);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public VideoDto getVideo(String id, int pageInd) throws ServiceException {
        try {
            Video video = videoRepository.findById(UUID.fromString(id)).orElseThrow();
            Page<VideoCover> additionalVideos = searchService.getVideoCollection(VideoCollectionType.RANDOM, null, pageInd);
            return converter.from(video, additionalVideos);

        } catch (IllegalArgumentException | NoSuchElementException ex) {
            throw new ContentNotFoundException("video");
        }
    }

    @Override
    public VideoDto getVideoRegardingUser(String id, int pageInt, UserDto userDto) throws ServiceException {
        VideoDto videoDto = getVideo(id, pageInt);
        Optional<View> viewOptional = viewRepository.findById(UUID.fromString(id), userDto.getId());
        viewOptional.ifPresent(view -> videoDto.setReaction(view.getReaction()));
        return videoDto;
    }
}
