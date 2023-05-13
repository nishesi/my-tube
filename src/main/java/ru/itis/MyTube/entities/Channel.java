package ru.itis.MyTube.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "channels")
public class Channel extends Content implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, length = 3000)
    private String info;
}
