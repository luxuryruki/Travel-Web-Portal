package com.example.travewebportal.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AdminDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
        private String username;
        private String email;
        private String password;

    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        private String username;
        private String email;
        private String password;
        private Long modifiedAt;
        private Long joinedAt;

    }
}
