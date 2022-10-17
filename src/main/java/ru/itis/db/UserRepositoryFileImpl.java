package ru.itis.db;

import ru.itis.dto.User;

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
        return new User(arr[0], arr[1], arr[2], arr[3], LocalDate.parse(arr[4]), arr[5], arr[6]);
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
                    String.valueOf(user.getBirthDate()),
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
