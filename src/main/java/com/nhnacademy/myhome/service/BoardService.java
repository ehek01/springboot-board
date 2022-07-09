package com.nhnacademy.myhome.service;

import com.nhnacademy.myhome.entity.Board;
import com.nhnacademy.myhome.repository.BoardRepository;
import com.nhnacademy.myhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Board save(String username, Board board) {
        userRepository.findByUsername(username).ifPresent(board::setUser);
        return boardRepository.save(board);
    }
}