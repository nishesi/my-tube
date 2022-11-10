package ru.itis.MyTube.model.dao.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.dao.ChannelRepository;
import ru.itis.MyTube.model.dto.Channel;
import ru.itis.MyTube.model.dto.ChannelCover;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class ChannelRepositoryJdbcImpl extends AbstractRepository implements ChannelRepository {
    private static final Function<ResultSet, ChannelCover> CHANNEL_COVER_MAPPER = set -> {
        try {
            return ChannelCover.builder()
                    .id(set.getLong("id"))
                    .name(set.getString("name"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    private static final Function<ResultSet, Channel> CHANNEL_MAPPER = set -> {
        try {
            return Channel.builder()
                    .id(set.getLong("id"))
                    .channelCover(CHANNEL_COVER_MAPPER.apply(set))
                    .info(set.getString("info"))
                    .countOfSubscribers(set.getLong("subs_count"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };
    private static final String SQL_GET_SUBSCRIBED_CHANNELS = "select * " +
            "from channel_covers " +
            "where id in (select channel_id from users_subscriptions where username = 'NishEsI');";
    private static final String SQL_GET_CHANNEL = "select * from channels_inf where id = ?";
    private final DataSource dataSource;

    @Override
    public List<ChannelCover> getSubscribedChannels(String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_SUBSCRIBED_CHANNELS)) {

            preparedStatement.setString(1, username);

            return transfer(preparedStatement.executeQuery(), CHANNEL_COVER_MAPPER);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Channel> get(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_CHANNEL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ?
                    Optional.of(CHANNEL_MAPPER.apply(resultSet)) :
                    Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
