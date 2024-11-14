package com.example.travewebportal.board.controller;

import com.example.travewebportal.board.dto.ArticleDto;
import com.example.travewebportal.board.enums.SearchType;
import com.example.travewebportal.board.repository.ArticleRepository;
import com.example.travewebportal.board.service.ArticleService;
import com.example.travewebportal.board.config.SecurityConfig;
import com.example.travewebportal.board.dto.ArticleWithCommentsDto;
import com.example.travewebportal.board.dto.UserAccountDto;
import com.example.travewebportal.board.service.PaginationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View Controller - Article")
@Import(SecurityConfig.class)
@WebMvcTest
class ArticleControllerTest {

    private MockMvc mvc;

    @MockBean
    private ArticleService articleService ;
    @MockBean
    private PaginationService paginationService;
    @Autowired
    private ArticleRepository articleRepository;


    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }



    @DisplayName("[View][GET] Article List")
    @Test
    public void given_whenRequestingArticlesListView_thenReturnArticlesListView() throws Exception {

        //given
        given(articleService.searchArticles(eq(null),eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"));
//                .andExpect(model().attributeExists("searchType"));
        then(articleService).should().searchArticles(eq(null),eq(null), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(),anyInt());
    }

    @DisplayName("[View][GET] Article List - search")
    @Test
    public void givenSearchKeyword_whenSearchingArticleView_thenReturnArticleView() throws Exception {

        //given
        SearchType searchType = SearchType.TITLE;
        String searchValue = "title";
        given(articleService.searchArticles(eq(searchType),eq(searchValue), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));
        mvc.perform(
                get("/articles")
                        .queryParam("searchType",searchType.name())
                        .queryParam("searchValue",searchValue)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("searchTypes"));
//                .andExpect(model().attributeExists("searchType"));
        then(articleService).should().searchArticles(eq(searchType),eq(searchValue), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(),anyInt());
    }


    @DisplayName("검색어 없이 게시글을 해시태그 검색하면, 빈 페이지를 반환한다.")
    @Test
    public void givenNoSearchKeyword_whenSearchingArticleViaHashtag_thenReturnEmptyPage() throws Exception {

        //given
        Pageable pageable = Pageable.ofSize(20);

        //when
        Page<ArticleDto> articles = articleService.searchArticlesViaHashtage(null, pageable);

        //then
        Assertions.assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).shouldHaveNoInteractions();
    }

    @DisplayName("게시글을 해스태그 검색하면, 게시글 페이지를 반환한다.")
    @Test
    public void givenSearchKeyword_whenSearchingArticleViaHashtag_thenReturnArticlePage() throws Exception {

        //given
        String hashtag = "#java";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByHashtagContaining(hashtag,pageable)).willReturn(Page.empty(pageable));

        //when
        Page<ArticleDto> articles = articleService.searchArticlesViaHashtage(hashtag, pageable);

        //then
        Assertions.assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).should().findByHashtagContaining(hashtag,pageable);
    }

    @DisplayName("해시태그 조회하면, 유니크 해시태그 리스트를 반환한다.")
    @Test
    public void givenNothing_whenCalling_thenReturnHashtags() throws Exception {

        //given
        List<String> expectedHashtags = List.of("#java", "#spring", "#boot");
        given(articleRepository.findAllDistinctHashtags()).willReturn(expectedHashtags);

        //when
        List<String> actualHashtags = articleService.getHashtags();

        //then
        Assertions.assertThat(actualHashtags).isEqualTo(expectedHashtags);
        then(articleRepository).should().findAllDistinctHashtags();
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 페이징, 정렬 기능")
    @Test
    void givenPagingAndSortingParams_whenSearchingArticlesView_thenReturnsArticlesView() throws Exception {
        // Given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortName)));
        List<Integer> barNumbers = List.of(1, 2, 3, 4, 5);
        given(articleService.searchArticles(null, null, pageable)).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages())).willReturn(barNumbers);

        // When & Then
        mvc.perform(
                        get("/articles")
                                .queryParam("page", String.valueOf(pageNumber))
                                .queryParam("size", String.valueOf(pageSize))
                                .queryParam("sort", sortName + "," + direction)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attribute("paginationBarNumbers", barNumbers));
        then(articleService).should().searchArticles(null, null, pageable);
        then(paginationService).should().getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages());
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

// bn gv        okip

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