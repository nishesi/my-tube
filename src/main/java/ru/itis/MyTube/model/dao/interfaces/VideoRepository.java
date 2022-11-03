package ru.itis.MyTube.model.dao.interfaces;

import ru.itis.MyTube.model.dto.VideoCover;

import java.util.List;

public interface VideoRepository {
    List<VideoCover> getVideosByName(String substring);
}
