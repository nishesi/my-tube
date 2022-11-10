package ru.itis.MyTube.model.dao.implementations;

import ru.itis.MyTube.model.dao.UserRepository;
import ru.itis.MyTube.model.dto.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserRepositoryJdbcImpl extends AbstractRepository implements UserRepository {

    private static final Function<ResultSet, User> USER_MAPPER = (set) -> {
        try {
            return User.builder()
                    .username(set.getString("username"))
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
            "country = ? " +
            "where username = ? ";
    private final DataSource dataSource;

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
    public boolean save(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setDate(5,
                    new Date(Date.from(user.getBirthdate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            preparedStatement.setString(6, user.getCountry());

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Optional<User> get(String login, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(USER_MAPPER.apply(resultSet));
            } else {
                return Optional.empty();
            }

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
            preparedStatement.setString(6, user.getUsername());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isPresent(String username) {
        return getAll().stream().anyMatch(user -> user.getUsername().equals(username));
    }
}
