package ru.itis.MyTube.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import ru.itis.MyTube.entities.enums.Authority;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "char(60)", nullable = false)
    private String password;

    @Column(nullable = false, length = 15)
    private String firstName;

    @Column(nullable = false, length = 15)
    private String lastName;

    @Column(nullable = false, length = 15)
    private LocalDate birthdate;

    @Column(nullable = false, length = 20)
    private String country;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Channel channel;

    @ManyToMany
    @JoinTable
    private List<Channel> subscriptions;

    @Enumerated(EnumType.STRING)
    private List<Authority> authorities;
}
