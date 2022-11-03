package ru.itis.MyTube.model.dao.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.dao.interfaces.VideoRepostiory;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class VideoRepositoryJdbcImpl implements VideoRepostiory {
    private final DataSource dataSource;
}
