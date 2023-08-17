package com.example.auth.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "confirmations")
public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String token;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    public LocalDateTime createAt;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    public User user;

    public Confirmation(User user) {
        this.token = UUID.randomUUID().toString();
        this.createAt = LocalDateTime.now();
        this.user = user;
    }
}
