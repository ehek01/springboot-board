package com.nhnacademy.myhome.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Column(name = "user_name", unique = true)
    private String username;

    private String password;

    private boolean enabled;

    @ManyToMany // 혹시 user rep 에서 roles 가 저장 될 때 같이 반영이 되도록.
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    // mappedBy 는 ref entity 의 변수명. // orphanRemoval = true -> 기존 데이터 날리고 새로 입력받은 데이터로만 boards 를 저장. (부모가 없는 데이터는 지운다.)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // 사용자 입장에서 게시글을 가져올땐 일대다 관계이다.
    private List<Board> boards = new ArrayList<>();
}
