package ru.itis.nishesi.MyTube.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.nishesi.MyTube.entities.Comment;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Comment.CommentId> {
    Page<Comment> findByVideoUuid(UUID videoId, Pageable pageable);
}
