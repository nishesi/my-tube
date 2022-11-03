package ru.itis.MyTube.model.services.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.dao.interfaces.VideoRepostiory;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.VideoService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepostiory videoRepostiory;

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
        return null;
    }
}
