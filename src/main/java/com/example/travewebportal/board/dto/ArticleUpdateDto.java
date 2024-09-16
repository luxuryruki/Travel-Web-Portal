package com.example.travewebportal.board.dto;

import com.example.travewebportal.board.domain.UserAccount;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleUpdateDto(
        String title,
        String content,

        String hashTag) {

    public static ArticleUpdateDto of(String title, String content, String hashTag) {
        return new ArticleUpdateDto(title,content, hashTag);
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
