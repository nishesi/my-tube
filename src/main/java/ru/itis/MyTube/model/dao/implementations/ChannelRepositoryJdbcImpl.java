package ru.itis.MyTube.model.dao.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.dao.interfaces.ChannelRepository;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class ChannelRepositoryJdbcImpl implements ChannelRepository {
    private final DataSource dataSource;
}
