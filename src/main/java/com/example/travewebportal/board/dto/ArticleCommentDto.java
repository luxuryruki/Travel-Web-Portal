package com.example.travewebportal.board.dto;

import java.time.LocalDateTime;

public record ArticleCommentDto(

        Long id,
        Long articleId,

        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String content
) {

    public static ArticleCommentDto of(Long id, Long articleId, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, String content) {
        return new ArticleCommentDto(id, articleId, createdAt, createdBy, modifiedAt, modifiedBy, content);
    }
}
