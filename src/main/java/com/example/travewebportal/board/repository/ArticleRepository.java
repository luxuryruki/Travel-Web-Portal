package com.example.travewebportal.board.repository;

import com.example.travewebportal.board.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByTitle(String title, Pageable pageable);
    void deleteByIdAndUserAccount_UserId(Long articleId, String userid);
}
