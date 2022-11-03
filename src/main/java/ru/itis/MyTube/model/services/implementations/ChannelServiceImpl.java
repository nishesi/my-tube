package ru.itis.MyTube.model.services.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.dao.interfaces.ChannelRepository;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.ChannelService;

import java.util.List;

@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;

    @Override
    public boolean addVideo(VideoCover videoCover, Video video) {
        return false;
    }

    @Override
    public boolean deleteVideo(Long cover_id) {
        return false;
    }

    @Override
    public boolean updateVideo(VideoCover videoCover, Video video) {
        return false;
    }

    @Override
    public List<VideoCover> getChannelVideoCovers(Long channelId) {
        return null;
    }
}
