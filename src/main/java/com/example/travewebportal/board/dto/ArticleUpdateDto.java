package com.example.travewebportal.board.dto;

import com.example.travewebportal.board.domain.UserAccount;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleUpdateDto(
        String title,
        String content,

        String hashtag) {

    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title,content, hashtag);
    }

    /**
     * DTO for {@link UserAccount}
     */
    @Value
    public static class UserAccountDto implements Serializable {
        LocalDateTime createdAt;
        String createdBy;
        LocalDateTime modifiedAt;
        String modifiedBy;
        Long id;
        String userId;
        String password;
        String email;
        String nickname;
        String memo;
    }
}
