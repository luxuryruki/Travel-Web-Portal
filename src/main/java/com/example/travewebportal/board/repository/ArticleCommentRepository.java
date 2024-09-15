package com.example.travewebportal.board.repository;

import com.example.travewebportal.board.domain.Article;
import com.example.travewebportal.board.domain.ArticleComment;
import com.example.travewebportal.board.dto.ArticleCommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
    List<ArticleComment> findByArticle_Id(Long id);

}
