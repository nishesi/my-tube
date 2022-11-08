package ru.itis.MyTube.model.dao.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.dao.interfaces.ChannelRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ChannelRepositoryJdbcImpl implements ChannelRepository {
    private final DataSource dataSource;
    private static final String SQL_GET_SUBSCRIBED_CHANNELS = "select channel_id from users_subscriptions where username = ?";
    @Override
    public List<Long> getSubscribedChannelsId(String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_SUBSCRIBED_CHANNELS)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Long> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getLong("channel_id"));
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
