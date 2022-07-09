package com.nhnacademy.myhome.controller;

import com.nhnacademy.myhome.entity.Board;
import com.nhnacademy.myhome.entity.User;
import com.nhnacademy.myhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {
    private final UserRepository userRepository;

    @GetMapping("/users")
    List<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return userRepository.findById(id)
                .map(user -> {
//                    user.setTitle(newUser.getTitle());
//                    user.setContent(newUser.getContent());
//                    user.setBoards(newUser.getBoards());

                    // 새로운 유저의 보드를 저장하기 때문에 기존유저의 보드는 깔끔하게 정리를 헤준다.
                    user.getBoards().clear();
                    user.getBoards().addAll(newUser.getBoards());

                    // board 가 수정되었을 때, board entity 에 user 정보가 다대일로 있기 때문에 반복문을 돌려서 모든 board 에 user 정보를 mapping 해준 것이다.
                    for (Board board : user.getBoards()) {
                        board.setUser(user);
                    }
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setUserId(id);
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}
