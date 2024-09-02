package com.example.travewebportal.board.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("View Controller - Article")
@WebMvcTest
class ArticleControllerTest {

    private MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[View][GET] Article List")
    @Test
    public void given_whenRequestingArticlesListView_thenReturnArticlesListView() throws Exception {

        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));
//                .andDo(print());
    }

    @DisplayName("[View][GET] Read Article ")
    @Test
    public void given_whenRequestingArticlesView_thenReturnArticlesView() throws Exception {

        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));
//                .andDo(print());
    }

    @Disabled
    @DisplayName("[View][GET] Search Article ")
    @Test
    public void given_whenRequestingArticlesSearchView_thenReturnArticlesSearchView() throws Exception {

        mvc.perform(get("/article/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search"));
//                .andDo(print());
    }
    @Disabled
    @DisplayName("[View][GET] Hashtag Search Article ")
    @Test
    public void given_whenRequestingArticlesHashtagSearchView_thenReturnArticlesHashtagSearchView() throws Exception {

        mvc.perform(get("/article/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
//                .andDo(print());
    }
}