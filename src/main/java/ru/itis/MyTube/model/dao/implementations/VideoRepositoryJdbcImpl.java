package ru.itis.MyTube.model.dao.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.dao.VideoRepository;
import ru.itis.MyTube.model.dto.ChannelCover;
import ru.itis.MyTube.model.dto.Video;
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
import java.util.Optional;
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
                            .name(set.getString("ch_name"))
                            .build())
                    .addedDate(LocalDateTime.parse(set.getString("added_date").substring(0, 19), formatter))
                    .duration(LocalTime.parse(set.getString("duration")))
                    .views(set.getLong("views"))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    private static final Function<ResultSet, Video> VIDEO_MAPPER = set -> {
        try {
            return Video.builder()
                    .uuid(UUID.fromString(set.getString("uuid")))
                    .videoCover(VIDEO_COVER_MAPPER.apply(set))
                    .info(set.getString("info"))
                    .likes(set.getLong("likes"))
                    .dislikes(set.getLong("dislikes")).build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };
    private static final String SQL_GET_VIDEOS_BY_SUBSTRING = "select * from video_covers where v_name like ?";
    private static final String SQL_GET_VIDEO = "select * from videos_inf where uuid = ?";
    private static final String SQL_ADD_VIDEO = "insert into videos (uuid, name, added_date, channel_id, duration, info) values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_GET_CHANNEL_VIDEOS = "select * from videos_inf where ch_id = ?";
    private final DataSource dataSource;

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

    @Override
    public Optional<Video> getVideo(UUID uuid) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_VIDEO)) {

            preparedStatement.setObject(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(VIDEO_MAPPER.apply(resultSet));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addVideo(Video video) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_VIDEO)) {

            preparedStatement.setObject(1, video.getUuid());
            preparedStatement.setString(2, video.getVideoCover().getName());
            preparedStatement.setObject(3, video.getVideoCover().getAddedDate());
            preparedStatement.setLong(4, video.getVideoCover().getChannelCover().getId());
            preparedStatement.setObject(5, video.getVideoCover().getDuration());
            preparedStatement.setString(6, video.getInfo());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<VideoCover> getChannelVideos(Long channelId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_CHANNEL_VIDEOS)) {

            preparedStatement.setLong(1, channelId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<VideoCover> videoCovers = new ArrayList<>(resultSet.getFetchSize());
            while (resultSet.next()) {
                videoCovers.add(VIDEO_COVER_MAPPER.apply(resultSet));
            }
            return videoCovers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String SQL_GET_RANDOM_VIDEOS = "select * from video_covers where random() < 0.01 limit 100";
    @Override
    public List<VideoCover> getRandomVideos() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_RANDOM_VIDEOS)){

            ResultSet resultSet = preparedStatement.executeQuery();
            List<VideoCover> videoCovers = new ArrayList<>(resultSet.getFetchSize());
            while(resultSet.next()) {
                videoCovers.add(VIDEO_COVER_MAPPER.apply(resultSet));
            }
            return videoCovers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
