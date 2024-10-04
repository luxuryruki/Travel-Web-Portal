package com.example.travewebportal.board.controller;

import com.example.travewebportal.board.ArticleService;
import com.example.travewebportal.board.config.SecurityConfig;
import com.example.travewebportal.board.dto.ArticleDto;
import com.example.travewebportal.board.dto.ArticleWithCommentsDto;
import com.example.travewebportal.board.dto.UserAccountDto;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("View Controller - Article")
@Import(SecurityConfig.class)
@WebMvcTest
class ArticleControllerTest {

    private MockMvc mvc;

    @MockBean
    private ArticleService articleService ;

    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }



    @DisplayName("[View][GET] Article List")
    @Test
    public void given_whenRequestingArticlesListView_thenReturnArticlesListView() throws Exception {

        //given
        given(articleService.searchArticles(eq(null),eq(null), any(Pageable.class))).willReturn(Page.empty());

        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));
//                .andExpect(model().attributeExists("searchType"));
        then(articleService).should().searchArticles(eq(null),eq(null), any(Pageable.class));
    }

    @DisplayName("[View][GET] Read Article ")
    @Test
    public void given_whenRequestingArticlesView_thenReturnArticlesView() throws Exception {

        //given
        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());

        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));
        then(articleService).should().getArticle(articleId);
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

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "johnny",
                LocalDateTime.now(),
                "johnny"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "johnny",
                "pw",
                "johnny@mail.com",
                "Johnny",
                "memo",
                LocalDateTime.now(),
                "johnny",
                LocalDateTime.now(),
                "johnny"
        );
    }
}