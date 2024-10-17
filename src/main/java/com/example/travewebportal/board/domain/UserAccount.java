package com.example.travewebportal.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "email"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")

}) //indexEs 는 검색 조건
@Entity
public class UserAccount extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String userId;
    @Setter
    @Column(nullable = false)
    private String password;
    @Setter
    private String email;
    @Setter
    private String nickname;
    private String memo;

    protected UserAccount() {
    }

    private UserAccount(String userId, String password, String email, String nickname, String memo) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }


    //factory method
    public static UserAccount of(String userId, String password, String email, String nickname, String memo) {
        return new UserAccount(userId, password, email, nickname, memo);
    }

    //동등성 동일성 검사
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount article)) return false;
        return id !=null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
