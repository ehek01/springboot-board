package com.nhnacademy.myhome.repository;

import com.nhnacademy.myhome.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
