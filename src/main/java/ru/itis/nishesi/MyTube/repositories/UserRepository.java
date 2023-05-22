package ru.itis.nishesi.MyTube.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.nishesi.MyTube.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByEmail(String email);
}
