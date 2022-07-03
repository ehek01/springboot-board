package com.nhnacademy.myhome.entity;

import lombok.Data;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true)
    private String username;

    private String password;

    private boolean enabled;
}
