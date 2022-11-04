package ru.itis.MyTube.model.dao.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.dao.interfaces.VideoRepository;
import ru.itis.MyTube.model.dto.VideoCover;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class VideoRepositoryJdbcImpl implements VideoRepository {
    private final DataSource dataSource;

    private static final String SQL_GET_VIDEOS_BY_SUBSTRING = "select * from videos_inf where name like '%?%'";
    @Override
    public List<VideoCover> getVideosByName(String substring) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_VIDEOS_BY_SUBSTRING)) {

            preparedStatement.setString(1, substring);

            ResultSet resultSet = preparedStatement.executeQuery();

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
