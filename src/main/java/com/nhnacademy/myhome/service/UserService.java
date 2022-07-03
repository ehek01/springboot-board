package com.nhnacademy.myhome.service;

import com.nhnacademy.myhome.entity.Role;
import com.nhnacademy.myhome.entity.User;
import com.nhnacademy.myhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setEnabled(true);

        Role role = new Role();
        role.setId(1L);
        user.getRoles().add(role);

        return userRepository.save(user);
    }
}
