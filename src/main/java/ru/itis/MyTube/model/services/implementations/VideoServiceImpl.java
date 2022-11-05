package ru.itis.MyTube.model.services.implementations;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.Type;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.validators.SearchValidator;
import ru.itis.MyTube.model.dao.interfaces.VideoRepository;
import ru.itis.MyTube.model.dto.ChannelCover;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.model.services.VideoService;

import java.util.List;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final SearchValidator searchValidator;
    private final UrlCreator urlCreator;

    @Override
    public VideoCover getVideoCover(UUID uuid) {
        return null;
    }

    @Override
    public Video getVideo(UUID uuid) {
        return null;
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
