package com.nhnacademy.myhome.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=2, max=30, message = "제목은 2자이상 30자 이하입니다.")
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id") // --> Board table 의 user_id 컬럼과, user table 의 id 컬럼이랑 조인하겠다.
    @JsonIgnore // 재귀호출 방지 -> user 안에는 board 값이 들어있지만 board 안에는 user 값이 들어있지 않도록 함. (스택오버플로우 방지.)
    private User user; //   게시글 입장에서는 다대일 관계가 형성된다. (유저 한명에 게시글이 여러개 있을 수 있기 때문이다.)

}
