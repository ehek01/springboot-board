package com.nhnacademy.myhome.controller;

import com.nhnacademy.myhome.entity.Board;
import com.nhnacademy.myhome.repository.BoardRepository;
import java.util.List;

import com.nhnacademy.myhome.validator.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);

        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            model.addAttribute("board", new Board());
        } else {
            boardRepository.findById(id)
                            .ifPresent(board -> model.addAttribute("board", board));
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult) {
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) { // TODO 6 : custom validator 는 동작을 하는데, entity 에 해둔 validation 은 적용이 안된다 왜일까..
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}