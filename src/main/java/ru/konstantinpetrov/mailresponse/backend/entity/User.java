package ru.konstantinpetrov.mailresponse.backend.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Getter
    @Column(nullable = false, unique = true)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Getter
    @Column(nullable = false)
    private Roles role;

    @Column(name = "block_status", nullable = false)
    private BlockStatus blockStatus;

    @Column(nullable = false)
    private int answerCount;
    public String getUsername() {
        return name;
    }
}