package com.example.travewebportal.board;

import com.example.travewebportal.board.domain.Article;
import com.example.travewebportal.board.domain.UserAccount;
import com.example.travewebportal.board.dto.ArticleDto;
import com.example.travewebportal.board.dto.ArticleWithCommentsDto;
import com.example.travewebportal.board.dto.UserAccountDto;
import com.example.travewebportal.board.enums.SearchType;
import com.example.travewebportal.board.repository.ArticleRepository;
import com.example.travewebportal.board.service.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("business logic - Articles")
@ExtendWith(MockitoExtension.class) // Mock 테스트를 위한 어노테이션
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("Search articles without prams, then return articles")
    @Test
    void givenNoSearchParam_whenSearchingArticles_thenReturnArticleList(){

        //Given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());
        
        //When
        Page<ArticleDto> articles =  articleService.searchArticles(null,null, pageable); // Title, Content, Id, Name, Hashtag
        //Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("Search articles with prams, then return articles")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {
        // Given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = articleService.searchArticles(searchType, searchKeyword, pageable);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining(searchKeyword, pageable);
    }

    @DisplayName("Read an article, then return the article")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnArticle(){

        //Given
        Long id = 1L;
        Article article = createArticle();
        given(articleRepository.findById(id)).willReturn(Optional.of(article));

        //When
        ArticleWithCommentsDto dto =  articleService.getArticle(id);
        //Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title",article.getTitle())
                .hasFieldOrPropertyWithValue("content",article.getContent())
                .hasFieldOrPropertyWithValue("hashtag",article.getHashtag());
        then(articleRepository).should().findById(id);
    }

    @DisplayName("Read non-existent article, then return throw excepti on")
    @Test
    void givenNonexistentArticleId_whenSearchingArticle_thenReturnThrowsException(){

        //Given
        Long id = 0L;
        Article article = createArticle();
        given(articleRepository.findById(id)).willReturn(Optional.empty());

        //When
        Throwable t = catchThrowable(()->articleService.getArticle(id));
        //Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("not found an article : " + id);
        then(articleRepository).should().findById(id);
    }
    @DisplayName("create article")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle(){
        //Given
        ArticleDto dto = createArticleDto("title","content");
        given(articleRepository.save(any(Article.class))).willReturn(null);
        //When
        articleService.saveArticle(dto);

        //Then
        then(articleRepository).should().save(any(Article.class));
    }



    @DisplayName("update article")
    @Test
    void givenArticleModifiedInfo_whenUpdatingArticle_thenUpdatesArticle(){
        //Given
        Article article = createArticle();
        ArticleDto dto = createArticleDto("title","content" );
        given(articleRepository.getReferenceById(dto.id())).willReturn(article);

        //When
        articleService.updateArticle(dto);

        //Then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title",dto.title())
                .hasFieldOrPropertyWithValue("content",dto.content())
                .hasFieldOrPropertyWithValue("hashtag",dto.hashtag());
        then(articleRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("delete article")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle(){
        //Given
        Long articleId = 1L;
        willDoNothing().given(articleRepository).deleteById(articleId);
        //When
        articleService.deleteArticle(1L);

        //Then
        then(articleRepository).should().deleteById(articleId);
    }

    private Article createArticle(){
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "hashtag"
        );
    }


    private UserAccount createUserAccount(){
        return UserAccount.of(
                "Johnny",
                "pw",
                "johnny@google.com",
                "Johnny",
                null
        );
    }
    private UserAccountDto createUserAccountDto(){
        return UserAccountDto.of(
                "Johnny",
                "pw",
                "johnny@google.com",
                "Johnny",
                null,
                LocalDateTime.now(),
                "Johnny",
                LocalDateTime.now(),
                "Johnny"
        );
    }
    private ArticleDto createArticleDto() {
        return createArticleDto("title", "content");
    }

    private ArticleDto createArticleDto(String title, String content) {
        return ArticleDto.of(
                1L,
                createUserAccountDto(),
                title,
                content,
                null,
                LocalDateTime.now(),
                "Uno",
                LocalDateTime.now(),
                "Uno");
    }
}