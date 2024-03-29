package ru.itis.nishesi.MyTube.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.nishesi.MyTube.enums.Authority;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    @Enumerated(EnumType.STRING)
    private List<Authority> authorities = List.of();

    // Security

    @Column
    @Builder.Default
    private boolean isNonLocked = true;

    @Column
    @Builder.Default
    private boolean isEnabled = true;

    // OAuth

    @Column
    private boolean isExternal = false;

    public Optional<Channel> getChannel() {
        return Optional.ofNullable(channel);
    }
}
