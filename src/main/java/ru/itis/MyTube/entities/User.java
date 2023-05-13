package ru.itis.MyTube.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import ru.itis.MyTube.enums.Authority;

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
    private List<Authority> authorities;

    // Security

    @Column
    private boolean isNonLocked = true;

    @Column
    private boolean isEnabled = true;

    public Optional<Channel> getChannel() {
        return Optional.ofNullable(channel);
    }
}
