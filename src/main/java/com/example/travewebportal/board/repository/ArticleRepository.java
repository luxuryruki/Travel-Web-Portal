package com.example.travewebportal.board.repository;

import com.example.travewebportal.board.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByTitleContaining(String title, Pageable pageable);
    Page<Article> findByContentContaining(String content, Pageable pageable);
    Page<Article> findByUserAccount_UserId (String userI, Pageable pageable);
    Page<Article> findByUserAccount_NicknameContaining(String name, Pageable pageable);
    Page<Article> findByHashtagContaining(String hashTag, Pageable pageable);
}
