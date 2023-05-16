package ru.itis.nishesi.MyTube.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.itis.nishesi.MyTube.entities.User;

import java.util.Optional;

public interface UserRepository extends
        CrudRepository<User, Long>,
        PagingAndSortingRepository<User, Long> {

    Optional<User> getByEmail(String email);
}
