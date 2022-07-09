package com.nhnacademy.myhome.service;

import com.nhnacademy.myhome.entity.Role;
import com.nhnacademy.myhome.entity.User;
import com.nhnacademy.myhome.repository.RoleRepository;
import com.nhnacademy.myhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User createUser(User user) {
        // 이미 가입된 유저라면
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            log.error("이미 가입된 유저입니다. username -> " + user.getUsername());
            throw new KeyAlreadyExistsException("이미 가입된 유저입니다. username -> " + user.getUsername());
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setEnabled(true);

        Role role = new Role();
        role.setId(1L); // ROLE_USER 가 role table 1번 인덱스에 들어가 있어서 id 값만 셋팅해주면 알아서 매핑됨. (내 추측)
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    public void createRole(String role) {
        Role newRole = new Role();
        newRole.setRoleName(role);
        roleRepository.save(newRole);
    }
}
