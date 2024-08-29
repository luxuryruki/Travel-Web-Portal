package com.example.travewebportal.board.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@DisplayName("Data Rest - API Test")
@Transactional // 테스트에서 동작하는 @Transactional 기능은 롤백
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[API] Get Article List")
    @Test
    void givenNothingWhenRequestingArticlesThenReturnArticlesJsonResponse() throws Exception {
        //given

        //when
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(print());

    }

    @DisplayName("[API] Read Article")
    @Test
    void givenNothingWhenRequestingArticleThenReturnArticleJsonResponse() throws Exception {
        //given

        //when
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(print());
    }

    @DisplayName("[API] Get Article Comments")
    @Test
    void givenNothingWhenRequestingArticleCommentThenReturnArticleCommentJsonResponse() throws Exception {
        //given

        //when
        mvc.perform(get("/api/articles/1/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(print());
    }

    @DisplayName("[API] Get Article Comment List")
    @Test
    void givenNothingWhenRequestingArticleCommentListThenReturnArticleCommentListJsonResponse() throws Exception {
        //given

        //when
        mvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(print());
    }

    @DisplayName("[API] Read Single Article Comment")
    @Test
    void givenNothingWhenRequestingSingleArticleCommentThenReturnSingleArticleCommentJsonResponse() throws Exception {
        //given

        //when
        mvc.perform(get("/api/articleComments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(print());
    }
}
