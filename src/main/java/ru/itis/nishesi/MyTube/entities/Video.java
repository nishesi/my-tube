package ru.itis.nishesi.MyTube.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "videos")
public class Video extends Content implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Basic
    @Column(length = 2000, nullable = false)
    private String info;

    @Column(nullable = false, length = 70)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Channel channel;

    @Column(nullable = false)
    private ZonedDateTime addedDate;

    private LocalTime duration;
}
