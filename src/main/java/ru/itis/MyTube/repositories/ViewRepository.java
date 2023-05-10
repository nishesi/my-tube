package ru.itis.MyTube.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itis.MyTube.entities.View;

import java.util.UUID;

public interface ViewRepository extends CrudRepository<View, View.ViewId> {
//    default View findById(UUID videoId, Long userId) {
//        return findById(new )
//    }
}
