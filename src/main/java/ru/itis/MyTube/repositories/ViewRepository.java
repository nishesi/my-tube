package ru.itis.MyTube.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itis.MyTube.entities.User;
import ru.itis.MyTube.entities.View;
import ru.itis.MyTube.entities.Video;
import ru.itis.MyTube.enums.Reaction;

import java.util.Optional;
import java.util.UUID;

public interface ViewRepository extends CrudRepository<View, View.ViewId> {
    default Optional<View> findById(UUID videoId, Long userId) {
        return findById(new View.ViewId(
                User.builder().id(userId).build(),
                Video.builder().uuid(videoId).build()
        ));
    }

    long countByReaction(Reaction reaction);
    long countByVideoUuid(UUID videoId);
}
