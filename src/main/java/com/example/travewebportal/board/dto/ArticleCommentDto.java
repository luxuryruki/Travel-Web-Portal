package com.example.travewebportal.board.dto;

import java.time.LocalDateTime;

public record ArticleCommentDto(

        Long articleId,

        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String content
) {

    public static ArticleCommentDto of(Long articleId, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, String content) {
        return new ArticleCommentDto(articleId, createdAt, createdBy, modifiedAt, modifiedBy, content);
    }
}
