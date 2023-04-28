package ru.itis.MyTube.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.itis.MyTube.dao.UserRepository;
import ru.itis.MyTube.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJdbcImpl extends AbstractRepository implements UserRepository {

    private static final Function<ResultSet, User> USER_MAPPER = (set) -> {
        try {
            return User.builder()
                    .email(set.getString("username"))
                    .password(set.getString("password"))
                    .firstName(set.getString("first_name"))
                    .lastName(set.getString("last_name"))
                    .birthdate(LocalDate.parse(set.getString("birthdate")))
                    .country(set.getString("country"))
                    .channelId((Long) set.getObject("channel_id"))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };
    private static final String SQL_GET_ALL_USERS = "select * from users;";
    private static final String SQL_SAVE_USER = "insert into users " +
            "(username, password, first_name, last_name, birthdate, country) " +
            "values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_GET_USER = "select * from users where username = ? and password = ?";
    private static final String SQL_UPDATE_USER = "update users set " +
            "password = ?," +
            " first_name = ?, " +
            "last_name = ?, " +
            "birthdate = ?, " +
            "country = ?, " +
            "channel_id = ?" +
            "where username = ? ";
    private static final String SQL_UNSUBSCRIBE = "delete from users_subscriptions where username = ? and channel_id = ?";
    private static final String SQL_SUBSCRIBE = "insert into users_subscriptions (username, channel_id) values (?, ?)";
    private static final String SQL_ADD_AUTHORITY = "insert into authorities (username, authority) VALUES (?, ?)";
    private final DataSource dataSource;

    @Override
    public List<User> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_USERS)) {

            return transfer(preparedStatement.executeQuery(), USER_MAPPER);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setDate(5,
                    new Date(Date.from(user.getBirthdate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            preparedStatement.setString(6, user.getCountry());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> get(String login, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return (resultSet.next()) ?
                    Optional.of(USER_MAPPER.apply(resultSet)) :
                    Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String SQL_GET_USER_BY_LOGIN = "select * from users where username = ?";

    @Override
    public Optional<User> get(String login) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER_BY_LOGIN)) {

            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            return (resultSet.next()) ?
                    Optional.of(USER_MAPPER.apply(resultSet)) :
                    Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {

            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setObject(4, user.getBirthdate());
            preparedStatement.setString(5, user.getCountry());
            preparedStatement.setObject(6, user.getChannelId());
            preparedStatement.setString(7, user.getEmail());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isPresent(String username) {
        return getAll().stream().anyMatch(user -> user.getEmail().equals(username));
    }

    private static final String SQL_IS_SUBSCRIBED = "select * from users_subscriptions where username = ? and channel_id = ? limit 1";
    @Override
    public boolean isSubscribed(String username, long channelId) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_IS_SUBSCRIBED)) {

            preparedStatement.setString(1, username);
            preparedStatement.setLong(2, channelId);

            return preparedStatement.executeQuery().next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void subscribe(String username, long channelId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SUBSCRIBE)) {

            preparedStatement.setString(1, username);
            preparedStatement.setLong(2, channelId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unsubscribe(String username, long channelId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UNSUBSCRIBE)) {

            preparedStatement.setString(1, username);
            preparedStatement.setLong(2, channelId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addAuthority(String username, String authority) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_AUTHORITY)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, authority);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
