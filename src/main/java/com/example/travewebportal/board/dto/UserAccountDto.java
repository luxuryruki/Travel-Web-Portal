package com.example.travewebportal.board.dto;

import lombok.Setter;

import java.time.LocalDateTime;

public record UserAccountDto(
        Long id,
        String userId,
        String password,
        String email,
        String nickname,
        String memo,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static UserAccountDto of(Long id,
                                    String userId,
                                    String password,
                                    String email,
                                    String nickname,
                                    String memo,
                                    LocalDateTime createdAt,
                                    String createdBy,
                                    LocalDateTime modifiedAt,
                                    String modifiedBy) {
        return new UserAccountDto(id,userId,password,email,nickname,memo,createdAt,createdBy,modifiedAt, modifiedBy);
    }
}
