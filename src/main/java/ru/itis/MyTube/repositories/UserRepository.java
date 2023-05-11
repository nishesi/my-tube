package ru.itis.MyTube.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.itis.MyTube.entities.User;

public interface UserRepository extends
        CrudRepository<User, Long>,
        PagingAndSortingRepository<User, Long> {
}
