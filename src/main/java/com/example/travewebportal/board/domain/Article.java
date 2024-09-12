package com.example.travewebportal.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashTag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")

}) //indexEs 는 검색 조건
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @Column(nullable = false)
    private String title;
    @Setter
    @Column(nullable = false, length = 1000)
    private String content;
    @Setter
    private String hashTag;

    @ToString.Exclude // 순환참조 방지
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {
    }

    private Article(UserAccount userAccount, String title, String content, String hashTag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashTag = hashTag;
    }

    //factory method
    public static Article of(UserAccount userAccount,String title, String content, String hashTag) {
        return new Article(userAccount, title, content, hashTag);
    }

    //동등성 동일성 검사
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id !=null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
