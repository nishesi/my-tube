package ru.itis.MyTube.model.dao.implementations;

import ru.itis.MyTube.model.dao.interfaces.UserRepository;
import ru.itis.MyTube.model.dto.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserRepositoryFileImpl implements UserRepository {

    private final String DELIMITER = ", ";

    private final Path STORAGE = Paths.get("D:\\Informatics\\StudyRoom\\FoISD\\WebProject\\storage.scv");

    private final Function<String, User> USER_MAPPER = (line) -> {
        String[] arr = line.split(DELIMITER);
        return User.builder()
                .login(arr[0])
                .password(arr[1])
                .firstName(arr[2])
                .lastName(arr[3])
                .birthdate(LocalDate.parse(arr[4]))
                .sex(arr[5])
                .country(arr[6])
                .build();
    };

    @Override
    public List<User> getAll() {
        try (Stream<String> lines = Files.lines(STORAGE)) {

                return lines
                        .filter(line -> !line.equals(""))
                        .map(USER_MAPPER)
                        .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(User user) {
        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(STORAGE.toFile(), true))) {

            String[] arr = new String[]{user.getLogin(),
                    user.getPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    String.valueOf(user.getBirthdate()),
                    user.getSex(),
                    user.getCountry()};

            for (int i = 0; i < arr.length -1 ; i++) {
                writer.write(arr[i] + DELIMITER);
            }
            writer.write(arr[arr.length-1]);
            writer.newLine();
            return true;
        } catch (IOException e) {
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
