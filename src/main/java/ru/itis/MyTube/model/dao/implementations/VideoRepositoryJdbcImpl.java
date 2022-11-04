package ru.itis.MyTube.model.dao.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.dao.interfaces.VideoRepository;
import ru.itis.MyTube.model.dto.ChannelCover;
import ru.itis.MyTube.model.dto.VideoCover;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
public class VideoRepositoryJdbcImpl implements VideoRepository {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");

    private static final Function<ResultSet, VideoCover> VIDEO_COVER_MAPPER = set -> {
        try {
            return VideoCover.builder()
                    .uuid(UUID.fromString(set.getString("uuid")))
                    .name(set.getString("v_name"))
                    .channelCover(ChannelCover.builder()
                            .id(set.getLong("ch_id"))
                            .name( set.getString("ch_name"))
                            .build())
                    .addedDate(LocalDateTime.parse(set.getString("added_date").substring(0, 19), formatter))
                    .duration(LocalTime.parse(set.getString("duration")))
                    .views(set.getLong("views"))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };
    private final DataSource dataSource;

    private static final String SQL_GET_VIDEOS_BY_SUBSTRING = "select * from video_covers where v_name like ?";
    @Override
    public List<VideoCover> getVideosByName(String substring) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_VIDEOS_BY_SUBSTRING)) {

            preparedStatement.setString(1, "%" + substring + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            List<VideoCover> list = new ArrayList<>(resultSet.getFetchSize());

            while (resultSet.next()) {
                list.add(VIDEO_COVER_MAPPER.apply(resultSet));
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
