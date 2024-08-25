package com.example.travewebportal.board.repository;

import com.example.travewebportal.board.domain.Article;
import com.example.travewebportal.board.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
