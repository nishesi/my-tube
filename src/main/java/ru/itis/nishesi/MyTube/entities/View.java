package ru.itis.nishesi.MyTube.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import ru.itis.nishesi.MyTube.enums.Reaction;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "views")
@IdClass(View.ViewId.class)
public class View {

    @Id
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(nullable = false)
    private Video video;

    @Enumerated(EnumType.STRING)
    @JoinColumn(nullable = false)
    private Reaction reaction = Reaction.NONE;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ViewId implements Serializable {
        private User user;
        private Video video;
    }
}
