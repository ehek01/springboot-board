package com.nhnacademy.myhome.repository;

import com.nhnacademy.myhome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
