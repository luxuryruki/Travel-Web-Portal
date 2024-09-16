package com.example.travewebportal.board;

import com.example.travewebportal.board.domain.Article;
import com.example.travewebportal.board.domain.UserAccount;
import com.example.travewebportal.board.dto.ArticleDto;
import com.example.travewebportal.board.dto.ArticleUpdateDto;
import com.example.travewebportal.board.dto.ArticleWithCommentDto;
import com.example.travewebportal.board.dto.UserAccountDto;
import com.example.travewebportal.board.enums.SearchType;
import com.example.travewebportal.board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.InstanceOfAssertFactories;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("business logic - Articles")
@ExtendWith(MockitoExtension.class) // Mock 테스트를 위한 어노테이션
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("Search articles, then return articles")
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
    @DisplayName("Search articles, then return articles")
    @Test
    void givenSearchParam_whenSearchingArticles_thenReturnArticles(){

        //Given
        SearchType searchType = SearchType.TITLE;
        String keyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitle(keyword, pageable)).willReturn(Page.empty());

        //When
        Page<ArticleDto> articles =  articleService.searchArticles(searchType,"search keyword", pageable); // Title, Content, Id, Name, Hashtag
        //Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitle(keyword,pageable);

    }



    @DisplayName("Read an article, then return the article")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnArticle(){

        //Given
        Long id = 1L;
        Article article = createArticle();
        given(articleRepository.findById(id)).willReturn(Optional.of(article));

        //When
        ArticleWithCommentDto dto =  articleService.getArticle(id);
        //Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title",article.getTitle())
                .hasFieldOrPropertyWithValue("content",article.getContent())
                .hasFieldOrPropertyWithValue("hashTag",article.getHashTag());
        then(articleRepository).should().findById(id);
    }

    @DisplayName("create article")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle(){
        //Given
        ArticleDto dto = ArticleDto.of(1L, createUserAccountDto(),"title","content","hashTag",LocalDateTime.now(),"Johnny",LocalDateTime.now(),"Johnny");
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
        ArticleUpdateDto dto = ArticleUpdateDto.of("title","content","hashTag");
        given(articleRepository.save(any(Article.class))).willReturn(null);
        //When
        articleService.updateArticle(1L, dto);

        //Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("delete article")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle(){
        //Given

        willDoNothing().given(articleRepository).delete(any(Article.class));
        //When
        articleService.deleteArticle(1L);

        //Then
        then(articleRepository).should().delete(any(Article.class));
    }

    private Article createArticle(){
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "hashTag"
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
                1L,
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
}