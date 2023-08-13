package com.example.auth.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "confirmations")
public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
