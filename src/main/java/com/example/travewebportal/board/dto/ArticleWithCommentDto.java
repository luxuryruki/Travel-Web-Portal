package com.example.travewebportal.board.dto;

import com.example.travewebportal.board.domain.ArticleComment;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleWithCommentDto(

        LocalDateTime createdAt,
        String createdBy,
        String title,
        String content,

        String hashTag,
        List<ArticleComment> comments) {

    public static ArticleWithCommentDto of(LocalDateTime createdAt, String createdBy, String title, String content, String hashTag, List<ArticleComment> comments) {
        return new ArticleWithCommentDto(createdAt,createdBy,title,content, hashTag, comments);
    }
}
