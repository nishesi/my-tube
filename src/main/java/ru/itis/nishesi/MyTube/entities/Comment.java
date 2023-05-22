package ru.itis.nishesi.MyTube.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
@IdClass(Comment.CommentId.class)
public class Comment {


    @Id
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(nullable = false)
    private Video video;

    private ZonedDateTime addedDate;

    @Column(nullable = false)
    private String text;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentId implements Serializable {
        private User user;
        private Video video;
    }
}
