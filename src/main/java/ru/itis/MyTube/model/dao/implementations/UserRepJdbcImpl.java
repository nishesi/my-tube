package ru.itis.MyTube.model.dao.implementations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.MyTube.model.dao.interfaces.UserRepository;
import ru.itis.MyTube.model.dto.User;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

//SINGLETON
public class UserRepJdbcImpl implements UserRepository {

    private final DataSource DATA_SOURCE;

    private static UserRepository repository;

    public static UserRepository getRepository() {
        if (repository == null) {
            repository = new UserRepJdbcImpl();
        }
        return repository;
    }

    private UserRepJdbcImpl() {
        try {

            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));

        DATA_SOURCE = new HikariDataSource(config);
    }

    private static final Function<ResultSet, User> USER_MAPPER = (set) -> {
        try {
            return User.builder()
                    .id(set.getLong("id"))
                    .login(set.getString("login"))
                    .password(set.getString("password"))
                    .firstName(set.getString("first_name"))
                    .lastName(set.getString("last_name"))
                    .birthdate(LocalDate.parse(set.getString("birth_date")))
                    .sex(set.getString("sex"))
                    .country(set.getString("country"))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    private static final String SQL_GET_ALL_USERS = "select * from users;";
    @Override
    public List<User> getAll() {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_USERS)) {

            ResultSet set = preparedStatement.executeQuery();
            ArrayList<User> list = new ArrayList<>(set.getFetchSize());

            while (set.next()) {
                list.add(USER_MAPPER.apply(set));
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    private static final String SQL_SAVE_USER = "insert into users " +
            "(login, password, first_name, last_name, birth_date, country, sex) " +
            "values (?, ?, ?, ?, ?, ?, ?)";
    @Override
    public boolean save(User user) {
        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER)) {

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setDate(5,
                     new Date(Date.from(user.getBirthdate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            preparedStatement.setString(6, user.getCountry());
            preparedStatement.setString(7, user.getSex());

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
    public Optional<User> get(String login) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public boolean isPresent(String username) {
        return getAll().stream().anyMatch(user -> user.getLogin().equals(username));
    }
}
